import java.util.Random;

public class Lis extends Zwierze{
	public Lis(int polX, int polY, Swiat swiat)
	{
        super(polX, polY, swiat);
        wiek = 0;
        sila = 3;
        inicjatywa = 7;
        oznaczenie = 'L';
        nazwa = "Lis";
    }

	public Lis(int polx, int poly, int sila, int inicjatywa, char ozn, int wiek, String nazwa, Swiat swiat,boolean zyje) {
        super(polx, poly, sila, inicjatywa,ozn, wiek, nazwa, swiat,zyje);
    }
	@Override
	public void akcja()
	{
		int ruch_x[] = { 1,-1,0,0 };
		int ruch_y[] = { 0,0,-1,1 };
		boolean wykonano = false;
		int sprkierunki[] = {0,0,0,0}; // tu {0} nie zeruje tablicy tylko jednoelementowa by byla2
		while (wykonano == false && wiek > 0)
		{
			Random rand = new Random();
			int losuj = rand.nextInt(4);
			int nowapoz_x = polozenie_x + ruch_x[losuj];
			int nowapoz_y = polozenie_y + ruch_y[losuj];
			if (nowapoz_x >= 0 && nowapoz_y >= 0 && nowapoz_x < swiat.getRozmiar_x() && nowapoz_y < swiat.getRozmiar_y())
			{
				Organizm przeciwnik = swiat.coStoi(nowapoz_x, nowapoz_y);
				if (przeciwnik == null) //nic nie stoi tam
				{
					//cout << nazwa << "rusza z (" << polozenie_x + 1 << "," << polozenie_y + 1 << ")";
					swiat.usunOznaczenie(polozenie_x, polozenie_y);
					polozenie_x = nowapoz_x;
					polozenie_y = nowapoz_y;
					//cout << "i idzie na (" << nowapoz_x + 1 << "," << nowapoz_y + 1 << ")" << endl;
					swiat.ustawOznaczenie(polozenie_x, polozenie_y, oznaczenie);
				}
				else
				{
					if (przeciwnik.getNazwa() == nazwa) //rozmnazanie
					{
						if (przeciwnik.getWiek() != 0)
							rozmnazanie();
						//else
							//cout << nazwa << " (" << polozenie_x + 1 << "," << polozenie_y + 1 << ") " << "zakaz rozmnazania z wiekiem 0" << endl;
					}
					else if(sila >= przeciwnik.getSila())// walka
					{
						kolizja(przeciwnik);
					}
					else
					{
						sprkierunki[losuj] = 1;
						if (sprkierunki[0] == 1 && sprkierunki[1] == 1 && sprkierunki[2] == 1 && sprkierunki[3] == 1)
							break;
						continue;
					}
				}
				wykonano = true;
			}
			sprkierunki[losuj] = 1;
		}
		//if(wykonano==false && wiek>0)
			//cout << nazwa << " (" << polozenie_x + 1 << "," << polozenie_y + 1 << ") nie ruszyl sie"<< endl;
		wiek++;
	}
	@Override
	public void wstawMlode(int x, int y, Swiat swiat){
		swiat.dodajOrganizm(new Lis(x, y, swiat));
	}
}
