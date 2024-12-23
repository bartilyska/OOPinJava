
public class Wilk extends Zwierze{
	public Wilk(int polX, int polY, Swiat swiat)
	{
        super(polX, polY, swiat);
        wiek = 0;
        sila = 9;
        inicjatywa = 5;
        oznaczenie = 'W';
        nazwa = "Wilk";
    }

	public Wilk(int polx, int poly, int sila, int inicjatywa, char ozn, int wiek, String nazwa, Swiat swiat,boolean zyje) {
        super(polx, poly, sila, inicjatywa,ozn, wiek, nazwa, swiat,zyje);
    }

	@Override
	public void wstawMlode(int x, int y, Swiat swiat) {
		swiat.dodajOrganizm(new Wilk(x, y, swiat));
	}
}
