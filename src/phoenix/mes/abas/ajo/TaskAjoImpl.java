/*
 * Domain típusok könyvtára.
 *
 * Created on Oct 6, 2017
 */

package phoenix.mes.abas.ajo;

import de.abas.erp.common.type.AbasDate;
import de.abas.erp.common.type.Id;
import de.abas.erp.common.type.IdImpl;
import de.abas.erp.common.type.enums.EnumFilingModeExtended;
import de.abas.erp.common.type.enums.EnumWorkOrderType;
import de.abas.erp.db.DatabaseIterator;
import de.abas.erp.db.DbContext;
import de.abas.erp.db.FieldSet;
import de.abas.erp.db.Query;
import de.abas.erp.db.field.TypedField;
import de.abas.erp.db.schema.company.TableOfUnits;
import de.abas.erp.db.schema.operation.Operation;
import de.abas.erp.db.schema.part.Product;
import de.abas.erp.db.schema.part.SelectablePart;
import de.abas.erp.db.schema.purchasing.Reservations;
import de.abas.erp.db.schema.purchasing.SelectablePurchasing;
import de.abas.erp.db.schema.workorder.SelectableWorkorder;
import de.abas.erp.db.schema.workorder.WorkOrders;
import de.abas.erp.db.selection.Conditions;
import de.abas.erp.db.selection.SelectionBuilder;
import de.abas.erp.db.type.AbasUnit;
import de.abas.erp.db.util.QueryUtil;

import java.util.HashSet;
import java.util.Set;

import phoenix.mes.abas.AbasConnection;
import phoenix.mes.abas.AbstractTask;
import phoenix.mes.abas.AbstractTaskDetails;

/**
 * Gyártási feladat osztálya, AJO-ban implementálva.
 * @author szizo
 */
public class TaskAjoImpl extends AbstractTask {

	/**
	 * Gyártási feladat részleteit leíró osztály, AJO-ban implementálva.
	 * @author szizo
	 */
	protected class DetailsAjoImpl extends AbstractTaskDetails {

		/**
		 * Az AJO-környezet.
		 */
		protected final DbContext ajoContext;

		/**
		 * Konstruktor.
		 * @param ajoContext Az AJO-környezet.
		 */
		protected DetailsAjoImpl(DbContext ajoContext) {
			this.ajoContext = ajoContext;
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.AbstractTaskDetails#getUnitName(de.abas.erp.db.type.AbasUnit)
		 */
		@Override
		protected String getUnitName(AbasUnit unit) {
			final SelectionBuilder<TableOfUnits.Row> criteria = SelectionBuilder.create(TableOfUnits.Row.class);
			criteria.setFilingMode(EnumFilingModeExtended.Active);
			criteria.add(Conditions.eq(TableOfUnits.Row.META.unitCode, unit));
			return QueryUtil.getFirst(ajoContext, criteria.build()).getUnitOperLang();
		}

		/* (non-Javadoc)
		 * @see phoenix.mes.abas.AbstractTaskDetails#loadFields()
		 */
		@Override
		protected void loadFields() {
			final WorkOrders workSlip = getWorkSlip(ajoContext);
			workSlipNo = workSlip.getIdno();
			usage = workSlip.getUsage();
			final AbasUnit setupTimeAbasUnit = workSlip.getSetupTimeUnit();
			setupTime = calculateGrossTime(workSlip.getSetupTime(), setupTimeAbasUnit, workSlip.getSetupTimeSec());
			setupTimeUnit = unitNamesRepository.getUnitName(setupTimeAbasUnit);
			final AbasUnit unitTimeAbasUnit = workSlip.getTimeUnit();
			unitTime = calculateGrossTime(workSlip.getTimeLimUnit(), unitTimeAbasUnit, workSlip.getUnitTimeSec());
			unitTimeUnit = unitNamesRepository.getUnitName(unitTimeAbasUnit);
			numberOfExecutions = workSlip.getElemQty();
			outstandingQuantity = workSlip.getUnitQty();
			loadDataFromProduct(workSlip.getProduct());
			loadDataFromOperation(workSlip.getSeriesOfOperations());
			loadDataFromOperationReservation(workSlip.getLastReservation());
		}

		/**
		 * A termékhez kapcsolódó tagváltozók kitöltése.
		 * @param productId A termék azonosítója.
		 */
		protected void loadDataFromProduct(SelectablePart productId) {
			final SelectionBuilder<SelectablePart> criteria = SelectionBuilder.create(SelectablePart.class);
			criteria.add(Conditions.eq(Product.META.id.getName(), productId.toString()));
			final Query<SelectablePart> query = ajoContext.createQuery(criteria.build());
			query.setFields(FieldSet.of(Product.META.idno.getName(),
					Product.META.swd.getName(),
					Product.META.descrOperLang.getName(),
					Product.META.yname2.getName(),
					Product.META.SU.getName()));
			query.setLazyLoad(false);
			final DatabaseIterator<SelectablePart> productIterator = query.iterator();
			final SelectablePart product = productIterator.next();
			productIterator.close();
			productIdNo = product.getIdno();
			productSwd = product.getSwd();
			productDescription = product.getString(Product.META.descrOperLang);
			if (product instanceof Product) {
				productDescription2 = ((Product)product).getYname2();
			}
			stockUnit = unitNamesRepository.getUnitName(product.getUnit(Product.META.SU.getName()));
		}

		/**
		 * A művelethez kapcsolódó tagváltozók kitöltése.
		 * @param operationId A művelet azonosítója.
		 */
		protected void loadDataFromOperation(Operation operationId) {
			final SelectionBuilder<Operation> criteria = SelectionBuilder.create(Operation.class);
			criteria.add(Conditions.eq(Operation.META.id, operationId));
			final Query<Operation> query = ajoContext.createQuery(criteria.build());
			final Set<TypedField<? super Operation>> fields = new HashSet<>(4);
			fields.add(Operation.META.idno);
			fields.add(Operation.META.swd);
			fields.add(Operation.META.descrOperLang);
			query.setFields(FieldSet.of(fields));
			query.setLazyLoad(false);
			final DatabaseIterator<Operation> operationIterator = query.iterator();
			final Operation operation = operationIterator.next();
			operationIterator.close();
			operationIdNo = operation.getIdno();
			operationSwd = operation.getSwd();
			operationDescription = operation.getDescrOperLang();
		}

		/**
		 * A műveletfoglaláshoz kapcsolódó tagváltozók kitöltése.
		 * @param operationReservationId A műveletfoglalás azonosítója.
		 */
		protected void loadDataFromOperationReservation(SelectablePurchasing operationReservationId) {
			final SelectionBuilder<SelectablePurchasing> criteria = SelectionBuilder.create(SelectablePurchasing.class);
			criteria.add(Conditions.eq(Reservations.META.id.getName(), operationReservationId.toString()));
			final Query<SelectablePurchasing> query = ajoContext.createQuery(criteria.build());
			query.setFields(FieldSet.of(Reservations.META.itemText.getName()));
			query.setLazyLoad(false);
			final DatabaseIterator<SelectablePurchasing> operationReservationIterator = query.iterator();
			final Reservations operationReservation = (Reservations)operationReservationIterator.next();
			operationReservationIterator.close();
			operationReservationText = operationReservation.getItemText();
		}

	}

	/**
	 * Az objektum kiírhatóságához kell.
	 */
	private static final long serialVersionUID = 2168632003172665470L;

	/**
	 * A megadott Abas-objektumazonosító továbbengedése, ha egy munkalapot azonosít.
	 * @param abasObjectId Az Abas-objektumazonosító.
	 * @param ajoContext Az AJO-környezet.
	 * @return Abas-munkalapazonosító.
	 * @throws IllegalArgumentException Ha az Abas-objektumazonosító nem egy létező munkalap azonosítója.
	 */
	protected static Id workSlipIdFilter(Id abasObjectId, DbContext ajoContext) {
		if (9 != abasObjectId.getDatabaseNo()) {
			throw new IllegalArgumentException("Nem munkalap-azonosító: " + abasObjectId);
		}
		final WorkOrders workSlip;
		try {
			workSlip = ajoContext.load(WorkOrders.class, abasObjectId);
		} catch (Exception e) {
			throw new IllegalArgumentException("Nem munkalap-azonosító: " + abasObjectId, e);
		}
		if (EnumWorkOrderType.WorkSlips != workSlip.getType()) {
			throw new IllegalArgumentException("Nem munkalap-azonosító: " + abasObjectId);
		}
		return abasObjectId;
	}

	/**
	 * Konstruktor.
	 * @param workSlipId A feladathoz tartozó munkalap azonosítója.
	 * @param ajoContext Az AJO-környezet.
	 */
	public TaskAjoImpl(Id workSlipId, DbContext ajoContext) {
		super(workSlipIdFilter(workSlipId, ajoContext));
	}

	/**
	 * Konstruktor.
	 * @param workSlip A feladathoz tartozó munkalap.
	 */
	TaskAjoImpl(SelectableWorkorder workSlip) {
		super(new IdImpl(workSlip.getRawString(WorkOrders.META.id.getName())));
	}

	/**
	 * @param ajoContext Az AJO-környezet.
	 * @return A feladathoz tartozó munkalap.
	 */
	public WorkOrders getWorkSlip(DbContext ajoContext) {
		return ajoContext.load(WorkOrders.class, workSlipId);
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task#getStartDate(phoenix.mes.abas.AbasConnection)
	 */
	@Override
	public AbasDate getStartDate(AbasConnection<?> abasConnection) {
		return getWorkSlip(AjoConnection.getAjoContext(abasConnection)).getStartDateDay();
	}

	/* (non-Javadoc)
	 * @see phoenix.mes.abas.Task#getDetails(phoenix.mes.abas.AbasConnection)
	 */
	@Override
	public Details getDetails(AbasConnection<?> abasConnection) {
		return (new DetailsAjoImpl(AjoConnection.getAjoContext(abasConnection)));
	}

/*
"1".equals(System.getProperty("de.abas.ceks.jedp.impl.nio"))

Feladat indítása
 Bemenet: munkamenet
 Ha van ysts időbélyeg: már fut, hiba
 ysts = G|sekundenz, yszeich = G|bzeichen

Lejelentés
 Bemenet: mennyiség, dolgozó (null is lehet), munkamenet
 Ha nincs ysts időbélyeg: nincs elindítva, hiba
 Anyagkivét?
 ysts = ""

Feladat megszakítása
 Bemenet: munkamenet
 ysts = "", yszeich = G|bzeichen
*/
}
