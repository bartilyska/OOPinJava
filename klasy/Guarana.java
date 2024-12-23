
public class Guarana extends Roslina{
	public Guarana(int polX, int polY, Swiat swiat)
	{
        super(polX, polY, swiat);
        wiek = 0;
        sila = 0;
        inicjatywa = 0;
        oznaczenie = 'G';
        nazwa = "Guarana";
        szansa_zasiania = 15;
    }

	public Guarana(int polx, int poly, int sila, int inicjatywa, char ozn, int wiek, String nazwa, Swiat swiat,boolean zyje) {
        super(polx, poly, sila, inicjatywa,ozn, wiek, nazwa, swiat,zyje);
        szansa_zasiania = 15;
    }
	@Override
	public void efektPoZjedzeniu(Organizm atakujacy)
	{
		atakujacy.setSila(atakujacy.getSila() + 3);
	}
	@Override
	public void wstawMlode(int x, int y, Swiat swiat) {
		swiat.dodajOrganizm(new Guarana(x, y, swiat));
	}
}
