
public class Owca extends Zwierze{
	public Owca(int polX, int polY, Swiat swiat)
	{
        super(polX, polY, swiat);
        wiek = 0;
        sila = 4;
        inicjatywa = 4;
        oznaczenie = 'O';
        nazwa = "Owca";
    }

	public Owca(int polx, int poly, int sila, int inicjatywa, char ozn, int wiek, String nazwa, Swiat swiat,boolean zyje) {
        super(polx, poly, sila, inicjatywa,ozn, wiek, nazwa, swiat,zyje);
    }

	@Override
	public void wstawMlode(int x, int y, Swiat swiat) {
		swiat.dodajOrganizm(new Owca(x, y, swiat));
	}
}
