package phoenix.mes.content;

import java.util.ArrayList;
import phoenix.mes.content.model.LanguageCollection;



public class LanguageSource {
	
	protected static final ArrayList<LanguageCollection> LANGUAGE_SOURCE = new ArrayList<LanguageCollection>();

	public static String getWord(String lng,int index)
	{
    	switch(lng) {
    	case "hu":
    	{
    		return LANGUAGE_SOURCE.get(index).hu;
    	}    	
    	case "de":
    	{
    		return LANGUAGE_SOURCE.get(index).de;
    	}
    	case "en":
    	{
    		return LANGUAGE_SOURCE.get(index).en;
    	}
    	default:
    	{
    		return LANGUAGE_SOURCE.get(index).hu;
    	}
    	}
	}
	public LanguageSource()
	{
		LANGUAGE_SOURCE.add(new LanguageCollection(0,"Felhasználónév","Benutzername","Username"));
		LANGUAGE_SOURCE.add(new LanguageCollection(1,"Jelszó","Kennwort","Password"));
		LANGUAGE_SOURCE.add(new LanguageCollection(2,"Tovább","Weiter","Next"));
		LANGUAGE_SOURCE.add(new LanguageCollection(3,"Bejelentkezés","Login","Login"));
		LANGUAGE_SOURCE.add(new LanguageCollection(4,"Munkaállomás","Arbeitsplatz","Workstation"));
		LANGUAGE_SOURCE.add(new LanguageCollection(5,"Munkalapszám","Arbeitsschein-Nr.","Worksheet No."));
		LANGUAGE_SOURCE.add(new LanguageCollection(6,"Cikkszám","Artikel-Nr.","Article"));
		LANGUAGE_SOURCE.add(new LanguageCollection(7,"Keresőszó","Suchwort","Search word"));
		LANGUAGE_SOURCE.add(new LanguageCollection(8,"Megnevezés","Bezeichung","Name"));
		LANGUAGE_SOURCE.add(new LanguageCollection(9,"Felhasználás","Verwendung","Place of use"));
		LANGUAGE_SOURCE.add(new LanguageCollection(10,"Hátralévő idő","Restlaufzeit","Remaining time"));
		LANGUAGE_SOURCE.add(new LanguageCollection(11,"Feladat indítása","Arbeit starten","Task start"));
		LANGUAGE_SOURCE.add(new LanguageCollection(12,"Megszakítás","Abbrechen","Interrupt"));
		LANGUAGE_SOURCE.add(new LanguageCollection(13,"Lejelentés","Rückmeldung","Submit"));
		LANGUAGE_SOURCE.add(new LanguageCollection(14,"Nyitott mennyiség","offene Menge","Open quantity"));
		LANGUAGE_SOURCE.add(new LanguageCollection(15,"Kész mennyiség","Fertigmenge","Finished quantity"));
		LANGUAGE_SOURCE.add(new LanguageCollection(16,"Küldés","Senden","Send"));
		LANGUAGE_SOURCE.add(new LanguageCollection(17,"Megszakítás indoka","Abbruchursache","Cause of interruption"));
		LANGUAGE_SOURCE.add(new LanguageCollection(18,"Új feladat kérése","Aufgabe anfordern","New task"));
		LANGUAGE_SOURCE.add(new LanguageCollection(19,"Minőségügyi probléma","QS-Problem","Quality problem"));
		LANGUAGE_SOURCE.add(new LanguageCollection(20,"Quality problem","Materialproblem","Material problem"));
		LANGUAGE_SOURCE.add(new LanguageCollection(21,"Géphiba","Maschinenausfall","Machine problem"));
		LANGUAGE_SOURCE.add(new LanguageCollection(22,"Műveleti azonosító","Arbeitsgang-Nr.","Operation number"));
		LANGUAGE_SOURCE.add(new LanguageCollection(23,"Dokumentumok","Dokumenten","Documents"));
		LANGUAGE_SOURCE.add(new LanguageCollection(24,"Darabjegyzék","Stückliste","Bill of Material"));
		LANGUAGE_SOURCE.add(new LanguageCollection(25,"Rendelési info","Auftragsinfo","Order info"));
		LANGUAGE_SOURCE.add(new LanguageCollection(26,"Beállítási idő","Rüstzeit","Setting time"));
		LANGUAGE_SOURCE.add(new LanguageCollection(27,"Darabidő","Einzelzeit","Time for pcs"));
		LANGUAGE_SOURCE.add(new LanguageCollection(28,"Nyitott mennyiség","offene Menge","Open quantity"));
		LANGUAGE_SOURCE.add(new LanguageCollection(29,"Gyártási információ","Produktionsinfo","Production info"));
		LANGUAGE_SOURCE.add(new LanguageCollection(30,"Megnyitás","Öffnen","Open"));
		LANGUAGE_SOURCE.add(new LanguageCollection(31,"Beépülő menny.","Menge","Plug-in quantity"));
		LANGUAGE_SOURCE.add(new LanguageCollection(32,"Bejelentkezve:","Angemeldet:","Logged in:"));
		LANGUAGE_SOURCE.add(new LanguageCollection(33,"Kijelentkezés","Abmelden","Logout"));
		LANGUAGE_SOURCE.add(new LanguageCollection(34,"Válasszon területet!","Wählen Sie einen Bereich!","Select an area!"));
		LANGUAGE_SOURCE.add(new LanguageCollection(35,"Frissítés","Aktualisieren","Refresh"));
		LANGUAGE_SOURCE.add(new LanguageCollection(36,"-tól","vom","-tól"));
		LANGUAGE_SOURCE.add(new LanguageCollection(37,"-ig","bis","-ig"));
		LANGUAGE_SOURCE.add(new LanguageCollection(38,"Hozzárendelt feladatok","zugeordnete Arbeitsgänge","Assigned tasks"));
		LANGUAGE_SOURCE.add(new LanguageCollection(39,"Feladatok indítása","Arbeitsgänge starten","Task start"));
		LANGUAGE_SOURCE.add(new LanguageCollection(40,"Munkaszám","Betriebsauftrag","Task number"));
		LANGUAGE_SOURCE.add(new LanguageCollection(41,"Megbízásiszám","Auftrags-Nr.","Order number"));
		LANGUAGE_SOURCE.add(new LanguageCollection(42,"Szöveg","Beschreibung","Article"));
		LANGUAGE_SOURCE.add(new LanguageCollection(43,"Beállítások","Optionen","Options"));
		LANGUAGE_SOURCE.add(new LanguageCollection(44,"Új nyitott mennyiség","neu offene Menge","New open quantity"));
		LANGUAGE_SOURCE.add(new LanguageCollection(45,"Alapértelmezettek","Default","Default"));
		LANGUAGE_SOURCE.add(new LanguageCollection(46,"Megszakítottak","Unterbrochen","Interrupted"));
		LANGUAGE_SOURCE.add(new LanguageCollection(47,"Mennyiség","Menge","Quantity"));
		LANGUAGE_SOURCE.add(new LanguageCollection(48,"Mentés","Speichern","Save"));
		LANGUAGE_SOURCE.add(new LanguageCollection(49,"Állomás:","Arbeitsplatz:","Station:"));
		LANGUAGE_SOURCE.add(new LanguageCollection(50,"Feladatok","Arbeitsgänge","Tasks"));
		LANGUAGE_SOURCE.add(new LanguageCollection(51,"Állomások","Arbeitsplätze","Stations"));
		LANGUAGE_SOURCE.add(new LanguageCollection(52,"Azonosítók","IDs","IDs"));
		LANGUAGE_SOURCE.add(new LanguageCollection(53,"Megszakítás","Unterbrechen","Interrupt"));
	}
}
