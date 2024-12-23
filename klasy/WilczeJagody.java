
public class WilczeJagody extends Roslina{
	public WilczeJagody(int polX, int polY, Swiat swiat)
	{
        super(polX, polY, swiat);
        wiek = 0;
        sila = 9999;
        inicjatywa = 0;
        oznaczenie = 'J';
        nazwa = "WilczeJagody";
        szansa_zasiania = 15;
    }

	public WilczeJagody(int polx, int poly, int sila, int inicjatywa, char ozn, int wiek, String nazwa, Swiat swiat,boolean zyje) {
        super(polx, poly, sila, inicjatywa,ozn, wiek, nazwa, swiat,zyje);
        szansa_zasiania = 15;
    }
	@Override
	public void efektPoZjedzeniu(Organizm atakujacy)
	{
		swiat.usunOznaczenie(polozenie_x, polozenie_y);
		swiat.usunOrganizm(this);
	}
	@Override
	public void wstawMlode(int x, int y, Swiat swiat) {
		swiat.dodajOrganizm(new WilczeJagody(x, y, swiat));
	}
}
