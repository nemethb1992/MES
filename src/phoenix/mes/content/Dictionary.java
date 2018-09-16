package phoenix.mes.content;


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
