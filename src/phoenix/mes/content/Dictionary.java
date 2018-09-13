package phoenix.mes.content;

import java.util.ArrayList;
import java.util.List;

import de.abas.erp.common.type.enums.EnumLanguageCode;


public class Dictionary {

	public enum Entry {

		USER_NAME("Felhasználónév","Benutzername","Username"),
		PASSWORD("Jelszó","Kennwort","Password"),
		NEXT("Tovább","Weiter","Next"),
		LOGIN("Bejelentkezés","Login","Login"),
		WORKSTATION("Munkaállomás","Arbeitsplatz","Workstation"),
		WORKSHEET_NO("Munkalapszám","Arbeitsschein-Nr.","Worksheet No."),
		ARTICLE("Cikkszám","Artikel-Nr.","Article"),
		SEARCH_WORD("Keresőszó","Suchwort","Search word"),
		NAME("Megnevezés","Bezeichung","Name"),
		PLACE_OF_USE("Felhasználás","Verwendung","Place of use"),
		REMAINING_TIME("Hátralévő idő","Restlaufzeit","Remaining time"),
		TASK_START("Feladat indítása","Arbeit starten","Task start"),
		INTERRUPT("Megszakítás","Abbrechen","Interrupt"),
		SUBMIT("Lejelentés","Rückmeldung","Submit"),
		OPEN_QUANTITY("Nyitott mennyiség","offene Menge","Open quantity"),
		FINISHED_QUANTITY("Kész mennyiség","Fertigmenge","Finished quantity"),
		SEND("Küldés","Senden","Send"),
		CAUSE_OF_INTERRUPTION("Megszakítás indoka","Abbruchursache","Cause of interruption"),
		NEW_TASK("Új feladat kérése","Aufgabe anfordern","New task"),
		QUALITY_PROBLEM("Minőségügyi probléma","QS-Problem","Quality problem"),
		MATERIAL_PROBLEM("Quality problem","Materialproblem","Material problem"),
		MACHINE_PROBLEM("Géphiba","Maschinenausfall","Machine problem"),
		OPERATION_NUMEBER("Műveleti azonosító","Arbeitsgang-Nr.","Operation number"),
		DOCUMENTS("Dokumentumok","Dokumenten","Documents"),
		BILL_OF_MATERIAL("Darabjegyzék","Stückliste","Bill of Material"),
		ORDER_INFO("Rendelési info","Auftragsinfo","Order info"),
		SETTING_TIME("Beállítási idő","Rüstzeit","Setting time"),
		TIME_FOR_PCS("Darabidő","Einzelzeit","Time for pcs"),
		PRODUCTION_INFO("Gyártási információ","Produktionsinfo","Production info"),
		OPEN("Megnyitás","Öffnen","Open"),
		PLUG_IN_QUANTITY("Beépülő menny.","Menge","Plug-in quantity"),
		LOGGED_IN("Bejelentkezve:","Angemeldet:","Logged in:"),
		LOGOUT("Kijelentkezés","Abmelden","Logout"),
		SELECT_AN_AREA("Válasszon területet!","Wählen Sie einen Bereich!","Select an area!"),
		REFRESH("Frissítés","Aktualisieren","Refresh"),
		ASSIGNED_TASKS("Hozzárendelt feladatok","zugeordnete Arbeitsgänge","Assigned tasks"),
		TASK_NUMBER("Munkaszám","Betriebsauftrag","Task number"),
		OREDR_NUMBER("Megbízásiszám","Auftrags-Nr.","Order number"),
		INFO_ARTICLE("Szöveg","Beschreibung","Article"),
		OPTIONS("Beállítások","Optionen","Options"),
		NEW_OPEN_QUANTITY("Új nyitott mennyiség","neu offene Menge","New open quantity"),
		DEFAULT("Alapértelmezettek","Default","Default"),
		INTERRUPTED("Megszakítottak","Unterbrochen","Interrupted"),
		QUANTITY("Mennyiség","Menge","Quantity"),
		SAVE("Mentés","Speichern","Save"),
		STATION("Állomás:","Arbeitsplatz:","Station:"),
		TASKS("Feladatok","Aufgaben","Tasks"),
		STATIONS("Állomások","Arbeitsplätze","Stations"),
		IDS("Azonosítók","IDs","IDs");

		private final String hungarianText;
		
		private final String germanText;
		
		private final String englishText;

		private Entry(String hungarianText, String germanText, String englishText) {
			this.hungarianText = hungarianText;
			this.germanText = germanText;
			this.englishText = englishText;
		}

		public String getTranslationIn(EnumLanguageCode language) {
			switch (language) {
				case German:
					return germanText;
				case English:
					return englishText;
				case Hungarian:
				default:
					return hungarianText;
			}
		}
		
		public String getHungarianText() {
			return hungarianText;
		}

		public String getGermanText() {
			return germanText;
		}
	
		public String getEnglishText() {
			return englishText;
		}

	}

	protected static class LanguageCollection {
	    public final String hu;
	    public final String de;
	    public final String en;
	    protected LanguageCollection(String h, String d, String e) {
	    	hu = h;
	    	de = d;
	    	en = e;
	    }
	}

//	protected static final List<LanguageCollection> LANGUAGE_SOURCE = new ArrayList<LanguageCollection>(60);
//
//	static {
//		LANGUAGE_SOURCE.add(new LanguageCollection("Felhasználónév","Benutzername","Username"));
//		LANGUAGE_SOURCE.add(new LanguageCollection("Jelszó","Kennwort","Password"));
//		LANGUAGE_SOURCE.add(new LanguageCollection("Tovább","Weiter","Next"));
//		LANGUAGE_SOURCE.add(new LanguageCollection("Bejelentkezés","Login","Login"));
//		LANGUAGE_SOURCE.add(new LanguageCollection("Munkaállomás","Arbeitsplatz","Workstation"));
//		LANGUAGE_SOURCE.add(new LanguageCollection("Munkalapszám","Arbeitsschein-Nr.","Worksheet No."));
//		LANGUAGE_SOURCE.add(new LanguageCollection("Cikkszám","Artikel-Nr.","Article"));
//		LANGUAGE_SOURCE.add(new LanguageCollection("Keresőszó","Suchwort","Search word"));
//		LANGUAGE_SOURCE.add(new LanguageCollection("Megnevezés","Bezeichung","Name"));
//		LANGUAGE_SOURCE.add(new LanguageCollection("Felhasználás","Verwendung","Place of use"));
//		LANGUAGE_SOURCE.add(new LanguageCollection("Hátralévő idő","Restlaufzeit","Remaining time"));
//		LANGUAGE_SOURCE.add(new LanguageCollection("Feladat indítása","Arbeit starten","Task start"));
//		LANGUAGE_SOURCE.add(new LanguageCollection("Megszakítás","Abbrechen","Interrupt"));
//		LANGUAGE_SOURCE.add(new LanguageCollection("Lejelentés","Rückmeldung","Submit"));
//		LANGUAGE_SOURCE.add(new LanguageCollection("Nyitott mennyiség","offene Menge","Open quantity"));
//		LANGUAGE_SOURCE.add(new LanguageCollection("Kész mennyiség","Fertigmenge","Finished quantity"));
//		LANGUAGE_SOURCE.add(new LanguageCollection("Küldés","Senden","Send"));
//		LANGUAGE_SOURCE.add(new LanguageCollection("Megszakítás indoka","Abbruchursache","Cause of interruption"));
//		LANGUAGE_SOURCE.add(new LanguageCollection("Új feladat kérése","Aufgabe anfordern","New task"));
//		LANGUAGE_SOURCE.add(new LanguageCollection("Minőségügyi probléma","QS-Problem","Quality problem"));
//		LANGUAGE_SOURCE.add(new LanguageCollection("Quality problem","Materialproblem","Material problem"));
//		LANGUAGE_SOURCE.add(new LanguageCollection("Géphiba","Maschinenausfall","Machine problem"));
//		LANGUAGE_SOURCE.add(new LanguageCollection("Műveleti azonosító","Arbeitsgang-Nr.","Operation number"));
//		LANGUAGE_SOURCE.add(new LanguageCollection("Dokumentumok","Dokumenten","Documents"));
//		LANGUAGE_SOURCE.add(new LanguageCollection("Darabjegyzék","Stückliste","Bill of Material"));
//		LANGUAGE_SOURCE.add(new LanguageCollection("Rendelési info","Auftragsinfo","Order info"));
//		LANGUAGE_SOURCE.add(new LanguageCollection("Beállítási idő","Rüstzeit","Setting time"));
//		LANGUAGE_SOURCE.add(new LanguageCollection("Darabidő","Einzelzeit","Time for pcs"));
//		LANGUAGE_SOURCE.add(new LanguageCollection("Nyitott mennyiség","offene Menge","Open quantity"));
//		LANGUAGE_SOURCE.add(new LanguageCollection("Gyártási információ","Produktionsinfo","Production info"));
//		LANGUAGE_SOURCE.add(new LanguageCollection("Megnyitás","Öffnen","Open"));
//		LANGUAGE_SOURCE.add(new LanguageCollection("Beépülő menny.","Menge","Plug-in quantity"));
//		LANGUAGE_SOURCE.add(new LanguageCollection("Bejelentkezve:","Angemeldet:","Logged in:"));
//		LANGUAGE_SOURCE.add(new LanguageCollection("Kijelentkezés","Abmelden","Logout"));
//		LANGUAGE_SOURCE.add(new LanguageCollection("Válasszon területet!","Wählen Sie einen Bereich!","Select an area!"));
//		LANGUAGE_SOURCE.add(new LanguageCollection("Frissítés","Aktualisieren","Refresh"));
//		LANGUAGE_SOURCE.add(new LanguageCollection("-tól","vom","-tól"));
//		LANGUAGE_SOURCE.add(new LanguageCollection("-ig","bis","-ig"));
//		LANGUAGE_SOURCE.add(new LanguageCollection("Hozzárendelt feladatok","zugeordnete Arbeitsgänge","Assigned tasks"));
//		LANGUAGE_SOURCE.add(new LanguageCollection("Feladatok indítása","Arbeitsgänge starten","Task start"));
//		LANGUAGE_SOURCE.add(new LanguageCollection("Munkaszám","Betriebsauftrag","Task number"));
//		LANGUAGE_SOURCE.add(new LanguageCollection("Megbízásiszám","Auftrags-Nr.","Order number"));
//		LANGUAGE_SOURCE.add(new LanguageCollection("Szöveg","Beschreibung","Article"));
//		LANGUAGE_SOURCE.add(new LanguageCollection("Beállítások","Optionen","Options"));
//		LANGUAGE_SOURCE.add(new LanguageCollection("Új nyitott mennyiség","neu offene Menge","New open quantity"));
//		LANGUAGE_SOURCE.add(new LanguageCollection("Alapértelmezettek","Default","Default"));
//		LANGUAGE_SOURCE.add(new LanguageCollection("Megszakítottak","Unterbrochen","Interrupted"));
//		LANGUAGE_SOURCE.add(new LanguageCollection("Mennyiség","Menge","Quantity"));
//		LANGUAGE_SOURCE.add(new LanguageCollection("Mentés","Speichern","Save"));
//		LANGUAGE_SOURCE.add(new LanguageCollection("Állomás:","Arbeitsplatz:","Station:"));
//		LANGUAGE_SOURCE.add(new LanguageCollection("Feladatok","Aufgaben","Tasks"));
//		LANGUAGE_SOURCE.add(new LanguageCollection("Állomások","Arbeitsplätze","Stations"));
//		LANGUAGE_SOURCE.add(new LanguageCollection("Azonosítók","IDs","IDs"));
//		LANGUAGE_SOURCE.add(new LanguageCollection("Megszakítás","Unterbrechen","Interrupt"));
//	}

	protected final EnumLanguageCode language;

	public Dictionary(EnumLanguageCode language) {
		this.language = language;
	}

	public EnumLanguageCode getLanguage() {
		return language;
	}

	public String getWord(Entry entry)
	{
		return entry.getTranslationIn(language);
	}

}
