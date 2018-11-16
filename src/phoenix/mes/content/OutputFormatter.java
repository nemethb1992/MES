package phoenix.mes.content;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import de.abas.erp.common.type.AbasDate;

public class OutputFormatter {

	public enum DictionaryEntry {

		USER_NAME("Felhasználónév","Benutzername","Username"),
		PASSWORD("Jelszó","Kennwort","Password"),
		NEXT("Tovább","Weiter","Next"),
		LOGIN("Bejelentkezés","Anmeldung","Login"),
		WORKSTATION("Munkaállomás","Arbeitsplatz","Workstation"),
		WORKSTATION_NAME("Munkaállomás neve","Arbeitsplatz Name","Workstation name"),
		WORKSHEET_NO("Munkalapszám","Arbeitsschein-Nr.","Worksheet No."),
		ARTICLE("Cikkszám","Artikel-Nr.","Article"),
		SEARCH_WORD("Keresőszó","Suchwort","Search word"),
		NAME("Megnevezés","Bezeichnung","Name"),
		PLACE_OF_USE("Felhasználás","Verwendung","Place of use"),
		REMAINING_TIME("Hátralévő idő","Restlaufzeit","Remaining time"),
		TASK_START("Feladat indítása","Arbeit starten","Task start"),
		START_TIME("Indítás ideje","Startzeit","Start time"),
		END_TIME("Várható befejezés","Erwarteter Abschluss","Expected completion"),
		INTERRUPT("Megszakítás","Abbrechen","Interrupt"),
		SUBMIT("Lejelentés","Rückmeldung","Submit"),
		EXECUTION_NO("Végrehajtás száma","Implementierungsnummer","Implementation number"),
		GET_STARTED("Tervezett kezdés","geplanter Start","Planned start"),
		CALCULATED_PROD_TIME("Gyártási idő","Produktionszeit","Production time"),
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
		SELECT_A_WORKSTATION("Válasszon állomást!","Wählen Sie eine Arbeitsstation!","Select a workstation!"),
		REFRESH("Frissítés","Aktualisieren","Refresh"),
		ASSIGNED_TASKS("Hozzárendelt feladatok","zugeordnete Arbeitsgänge","Assigned tasks"),
		TASK_NUMBER("Munkaszám","Betriebsauftrag","Task number"),
		OREDR_NUMBER("Megbízásiszám","Auftrags-Nr.","Order number"),
		INFO_ARTICLE("Szöveg","Beschreibung","Article"),
		OPTIONS("Beállítások","Optionen","Options"),
		NEW_OPEN_QUANTITY("Új nyitott mennyiség","neu offene Menge","New open quantity"),
		DEFAULT("Alapértelmezettek","Default","Default"),
		RESPONSIBLES("Felelősök","Verantwortliche","Responsibles"),
		INTERRUPTED("Megszakítottak","Unterbrochen","Interrupted"),
		QUANTITY("Mennyiség","Menge","Quantity"),
		SAVE("Mentés","Speichern","Save"),
		AREA("Terület","Bereich","Area"),
		GROUP("Gépsoport","Maschinengruppe","Group"),
		STATION("Állomás:","Arbeitsplatz:","Station:"),
		TASKS("Feladatok","Aufgaben","Tasks"),
		TASK("Feladat","Aufgabe","Task"),
		STATIONS("Állomások","Arbeitsplätze","Stations"),
		LOGIN_FAILED("Sikertelen bejelentkezés","Anmeldung fehlgeschlagen","Login failed"),
		LOGIN_FAILED_MISSING_WS("Nincs regisztrált munkaállomás","Fehlende registrierte Arbeitsstation","Missing registered workstation"),
		LOGIN_FAILED_EMPTY_CREDENTIALS("A felhasználónév és/vagy jelszó nincs megadva!","Ihr Benutzername und / oder Passwort ist nicht angegeben!","Your username and / or password is not specified!"),
		IDS("Azonosítók","IDs","IDs");

		private final String hungarianText;

		private final String germanText;

		private final String englishText;

		private DictionaryEntry(String hungarianText, String germanText, String englishText) {
			this.hungarianText = hungarianText;
			this.germanText = germanText;
			this.englishText = englishText;
		}

		public String getTranslationIn(Locale locale) {
			switch (locale.getLanguage()) {
				case "de":
					return germanText;
				case "en":
					return englishText;
				case "hu":
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

	public enum OperatingLanguage {

		hu(new Locale("hu")),
		de(Locale.GERMAN),
		en(Locale.ENGLISH);

		private final Locale locale;

		public static OperatingLanguage fromCode(String languageCode) {
			try {
				return OperatingLanguage.valueOf(languageCode);
			} catch (IllegalArgumentException e) {
				return null;
			}
		}

		private OperatingLanguage(Locale locale) {
			this.locale = locale;
		}

		public Locale getLocale() {
			return locale;
		}

	}

	public static final BigDecimal BIG_DECIMAL_3600 = new BigDecimal(3600);
	
	protected final Locale locale;

	protected final NumberFormat numberFormat;

	public static OutputFormatter forRequest(HttpServletRequest request) {
		OperatingLanguage language = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("language")) {
					language = OperatingLanguage.fromCode(cookie.getValue());
				}
			}
		}
		if (null == language) {
			language = OperatingLanguage.fromCode(request.getLocale().getLanguage());
		}
		return new OutputFormatter((null == language ? OperatingLanguage.hu : language).getLocale());
	}

	public static String isStation(String station)
	{
		String[] splitted = station.split("!");
		if(2 !=  splitted.length)
		{
			return null;
		}
		return station;
			
	}
	
	public OutputFormatter(Locale locale) {
		this.locale = locale;
		numberFormat = NumberFormat.getInstance(locale);
	}

	public Locale getLocale() {
		return locale;
	}

	public String getWord(DictionaryEntry dictionaryEntry)
	{
		return dictionaryEntry.getTranslationIn(locale);
	}

	public String format(BigDecimal number) {
		return numberFormat.format(number);
	}

	public String formatWithoutTrailingZeroes(BigDecimal number) {
		return format(number.stripTrailingZeros());
	}

	public String formatWithoutTrailingZeroes(BigDecimal number, int decimalPlaces) {
		return formatWithoutTrailingZeroes(number.setScale(decimalPlaces, RoundingMode.HALF_UP));
	}

	public String formatTime(BigDecimal timeInHours) {
		return formatTime(timeInHours.multiply(BIG_DECIMAL_3600).intValue());
	}
	
	public String formatDate(AbasDate date) {
		SimpleDateFormat sdf;
		
		switch (locale.getLanguage()) {
		case "de":
			sdf= new SimpleDateFormat("dd-mm-yyyyy");
			return sdf.format(date);
		case "en":
			sdf= new SimpleDateFormat("dd-mm-yyyyy");
			return sdf.format(date);
		case "hu":
		default:
			sdf= new SimpleDateFormat("yyyyy-mm-dd");
			return sdf.format(date);
		}
	}
	

	public String formatTime(int timeInSeconds) {
	    int hours = timeInSeconds / 3600;
	    int secondsLeft = timeInSeconds - hours * 3600;
	    final StringBuilder formattedTime = new StringBuilder(8);
	    formattedTime.append(hours).append(':');
	    int minutes = secondsLeft / 60;
	    if (minutes < 10) {
	        formattedTime.append('0');
	    }
	    formattedTime.append(minutes).append(':');
	    int seconds = secondsLeft - minutes * 60;
	    if (seconds < 10) {
	    	formattedTime.append('0');
	    }
	    formattedTime.append(seconds);
	    return formattedTime.toString();
	}
	

}

