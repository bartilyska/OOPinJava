import java.util.Random;

public class Mlecz extends Roslina{

	public Mlecz(int polX, int polY, Swiat swiat)
	{
        super(polX, polY, swiat);
        wiek = 0;
        sila = 0;
        inicjatywa = 0;
        oznaczenie = 'M';
        nazwa = "Mlecz";
        szansa_zasiania = 10;
    }

	public Mlecz(int polx, int poly, int sila, int inicjatywa, char ozn, int wiek, String nazwa, Swiat swiat,boolean zyje) {
        super(polx, poly, sila, inicjatywa,ozn, wiek, nazwa, swiat,zyje);
        szansa_zasiania = 10;
    }
	@Override
	public void akcja()
	{
		for (int i = 0; i < 3; i++)
		{
			boolean przerwij = false;
			Random rand = new Random();
			int losuj = rand.nextInt(100);
			if (losuj < szansa_zasiania)
			{
				int ruch_x[] = { 1,-1,0,0 };
				int ruch_y[] = { 0,0,-1,1 };
				int sprkierunki[] = {0,0,0,0 };
				while (wiek > 0)
				{
					int los = rand.nextInt(4);
					int nowapoz_x = polozenie_x + ruch_x[los];
					int nowapoz_y = polozenie_y + ruch_y[los];
					if (nowapoz_x >= 0 && nowapoz_y >= 0 && nowapoz_x < swiat.getRozmiar_x() && nowapoz_y < swiat.getRozmiar_y())
					{
						Organizm przeciwnik = swiat.coStoi(nowapoz_x, nowapoz_y);
						if (przeciwnik == null) //nic nie stoi tam
						{
							//cout << nazwa << " rozsiewa sie z (" << polozenie_x + 1 << "," << polozenie_y + 1 << ")";
							//cout << " na (" << nowapoz_x + 1 << "," << nowapoz_y + 1 << ")" << endl;
							utworzLogRozsianie(nowapoz_x, nowapoz_y);

							wstawMlode(nowapoz_x, nowapoz_y, swiat);
							swiat.ustawOznaczenie(nowapoz_x, nowapoz_y, oznaczenie);
							break;
						}
						sprkierunki[los] = 1;
					}
					sprkierunki[los] = 1;
					if (sprkierunki[0] == 1 && sprkierunki[1] == 1 && sprkierunki[2] == 1 && sprkierunki[3] == 1)
					{
						//cout << nazwa << " nie ma  miejsca by sie rozsiac" << endl;
						przerwij = true;
						break;
					}
				}
			}
			if (przerwij == true) //zeby nie probowac rozmnazac gdy i tak nie ma miejsca
				break;
		}
		wiek++;
	}
	@Override
	public void wstawMlode(int x, int y, Swiat swiat) {
		swiat.dodajOrganizm(new Mlecz(x, y, swiat));
	}
}