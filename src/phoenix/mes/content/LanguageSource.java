package phoenix.mes.content;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import de.abas.erp.common.type.enums.EnumLanguageCode;
import phoenix.mes.content.model.LanguageCollection;



public class LanguageSource {
	
	private static EnumLanguageCode AbasLanguage;
	public static void setAbasLanguage(EnumLanguageCode value)
	{
		AbasLanguage = value;
	}
	public static EnumLanguageCode getAbasLanguage()
	{
	    return AbasLanguage;
	}
	
	private static String Lng_type;
	public static void setLng_type(String value)
	{
		Lng_type = value;
	}
	public static String getLng_type()
	{
	    return Lng_type;
	}
	private static ArrayList<LanguageCollection> Lng;
	public static void setLng(ArrayList<LanguageCollection> value)
	{
		Lng = value;
	}
	public static ArrayList<LanguageCollection> getLng()
	{
	    return Lng;
	}	
	public String LanguageSelector(String lng,int index)
	{
    	switch(lng) {
    	case "hu":
    	{
    		return getLng().get(index).hu;
    	}    	
    	case "de":
    	{
    		return getLng().get(index).de;
    	}
    	case "en":
    	{
    		return getLng().get(index).en;
    	}
    	default:
    	{
    		return getLng().get(index).hu;
    	}
    	}
	}
	public ArrayList<LanguageCollection> LanguageList()
	{
		ArrayList<LanguageCollection> li = new ArrayList<LanguageCollection>();
		li.add(new LanguageCollection(0,"Felhasználónév","Benutzername","Username"));
		li.add(new LanguageCollection(1,"Jelszó","Kennwort","Password"));
		li.add(new LanguageCollection(2,"Tovább","Weiter","Next"));
		li.add(new LanguageCollection(3,"Bejelentkezés","Login","Login"));
		li.add(new LanguageCollection(4,"Munkaállomás","Arbeitsplatz","Workstation"));
		li.add(new LanguageCollection(5,"Munkalapszám","Arbeitsschein-Nr.","Worksheet No."));
		li.add(new LanguageCollection(6,"Cikkszám","Artikel-Nr.","Article"));
		li.add(new LanguageCollection(7,"Keresőszó","Suchwort","Search word"));
		li.add(new LanguageCollection(8,"Megnevezés","Bezeichung","Name"));
		li.add(new LanguageCollection(9,"Felhasználás","Verwendung","Place of use"));
		li.add(new LanguageCollection(10,"Hátralévő idő","Restlaufzeit","Remaining time"));
		li.add(new LanguageCollection(11,"Feladat indítása","Arbeit starten","Task start"));
		li.add(new LanguageCollection(12,"Megszakítás","Abbrechen","Interrupt"));
		li.add(new LanguageCollection(13,"Lejelentés","Rückmeldung","Submit"));
		li.add(new LanguageCollection(14,"Nyitott mennyiség","offene Menge","Open quantity"));
		li.add(new LanguageCollection(15,"Kész mennyiség","Fertigmenge","Finished quantity"));
		li.add(new LanguageCollection(16,"Küldés","Senden","Send"));
		li.add(new LanguageCollection(17,"Megszakítás indoka","Abbruchursache","Cause of interruption"));
		li.add(new LanguageCollection(18,"Új feladat kérése","Aufgabe anfordern","New task"));
		li.add(new LanguageCollection(19,"Minőségügyi probléma","QS-Problem","Quality problem"));
		li.add(new LanguageCollection(20,"Quality problem","Materialproblem","Material problem"));
		li.add(new LanguageCollection(21,"Géphiba","Maschinenausfall","Machine problem"));
		li.add(new LanguageCollection(22,"Műveleti azonosító","Arbeitsgang-Nr.","Operation number"));
		li.add(new LanguageCollection(23,"Dokumentumok","Dokumenten","Documents"));
		li.add(new LanguageCollection(24,"Darabjegyzék","Stückliste","Bill of Material"));
		li.add(new LanguageCollection(25,"Rendelési info","Auftragsinfo","Order info"));
		li.add(new LanguageCollection(26,"Beállítási idő","Rüstzeit","Setting time"));
		li.add(new LanguageCollection(27,"Darabidő","Einzelzeit","Time for pcs"));
		li.add(new LanguageCollection(28,"Nyitott mennyiség","offene Menge","Open quantity"));
		li.add(new LanguageCollection(29,"Gyártási információ","Produktionsinfo","Production info"));
		li.add(new LanguageCollection(30,"Megnyitás","Öffnen","Open"));
		li.add(new LanguageCollection(31,"Beépülő menny.","Menge","Plug-in quantity"));
		li.add(new LanguageCollection(32,"Bejelentkezve:","Angemeldet:","Logged in:"));
		li.add(new LanguageCollection(33,"Kijelentkezés","Abmelden","Logout"));
		li.add(new LanguageCollection(34,"Válasszon területet!","Wählen Sie einen Bereich!","Select an area!"));
		li.add(new LanguageCollection(35,"Frissítés","Aktualisieren","Refresh"));
		li.add(new LanguageCollection(36,"-tól","vom","-tól"));
		li.add(new LanguageCollection(37,"-ig","bis","-ig"));
		li.add(new LanguageCollection(38,"Hozzárendelt feladatok","zugeordnete Arbeitsgänge","Assigned tasks"));
		li.add(new LanguageCollection(39,"Feladatok indítása","Arbeitsgänge starten","Task start"));
		li.add(new LanguageCollection(40,"Munkaszám","Betriebsauftrag","Task number"));
		li.add(new LanguageCollection(41,"Megbízásiszám","Auftrags-Nr.","Order number"));
		li.add(new LanguageCollection(42,"Szöveg","Beschreibung","Article"));
		li.add(new LanguageCollection(43,"Beállítások","Optionen","Options"));
		li.add(new LanguageCollection(44,"Új nyitott mennyiség","neu offene Menge","New open quantity"));
		li.add(new LanguageCollection(45,"Alapértelmezettek","Default","Default"));
		li.add(new LanguageCollection(46,"Megszakítottak","Unterbrochen","Interrupted"));
		li.add(new LanguageCollection(47,"Mennyiség","Menge","Quantity"));
		li.add(new LanguageCollection(48,"Mentés","Speichern","Save"));
		li.add(new LanguageCollection(49,"Állomás:","Arbeitsplatz:","Station:"));
		li.add(new LanguageCollection(50,"Feladatok","Arbeitsgänge","Tasks"));
		li.add(new LanguageCollection(51,"Állomások","Arbeitsplätze","Stations"));
		li.add(new LanguageCollection(52,"Azonosítók","IDs","IDs"));
		li.add(new LanguageCollection(53,"Megszakítás","Unterbrechen","Interrupt"));
		return li;
	}
}
