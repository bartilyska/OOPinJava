import java.util.Random;
public class Antylopa extends Zwierze{
	public Antylopa(int polX, int polY, Swiat swiat)
	{
        super(polX, polY, swiat);
        wiek = 0;
        sila = 4;
        inicjatywa = 4;
        oznaczenie = 'A';
        nazwa = "Antylopa";
    }

	public Antylopa(int polx, int poly, int sila, int inicjatywa, char ozn, int wiek, String nazwa, Swiat swiat,boolean zyje) {
        super(polx, poly, sila, inicjatywa,ozn, wiek, nazwa, swiat,zyje);
    }
	@Override
	public boolean czyMozeUciec()
	{
		Random rand = new Random();
		int ucieczka = rand.nextInt(2);
		if (ucieczka == 0)
			return false;
		int ruch_x[] = { 1,-1,0,0 };
		int ruch_y[] = { 0,0,1,-1};
		for (int i = 0; i < 4; i++)
		{
			int nowapoz_x = polozenie_x + ruch_x[i];
			int nowapoz_y = polozenie_y + ruch_y[i];
			if (nowapoz_x >= 0 && nowapoz_y >= 0 && nowapoz_x < swiat.getRozmiar_x() && nowapoz_y < swiat.getRozmiar_y())
			{
				if (swiat.coStoi(nowapoz_x, nowapoz_y) == null) //nic nie stoi tam
					return true;
			}
		}
		return false;
	}
	@Override
	public void ucieczka(Organizm atakujacy)
	{
		int ruch_x[] = { 1,-1,0,0 };
		int ruch_y[] = { 0,0,1,-1 };//antylopa ucieka
		for (int i = 0; i < 4; i++)
		{
			int nowapoz_x = polozenie_x + ruch_x[i];
			int nowapoz_y = polozenie_y + ruch_y[i];
			if (nowapoz_x >= 0 && nowapoz_y >= 0 && nowapoz_x < swiat.getRozmiar_x() && nowapoz_y < swiat.getRozmiar_y())
			{
				if (swiat.coStoi(nowapoz_x, nowapoz_y) == null)
				{
					swiat.usunOznaczenie(atakujacy.getPolozenie_x(), atakujacy.getPolozenie_y());
					swiat.ustawOznaczenie(polozenie_x, polozenie_y, atakujacy.getOznacznie()); //ruszenie atakujacego i antylopy
					atakujacy.setPolozenie_x(polozenie_x);
					atakujacy.setPolozenie_y(polozenie_y);
					polozenie_x = nowapoz_x;
					polozenie_y = nowapoz_y;
					swiat.ustawOznaczenie(nowapoz_x, nowapoz_y, oznaczenie);
					utworzLogUcieczka(atakujacy);
					//cout << nazwa << " uciekla na (" << polozenie_x + 1 << "," << polozenie_y + 1 << ") przed " << atakujacy->getNazwa() << " z (" <<
						//atakujacy->getPolozenie_x() + 1 << "," << atakujacy->getPolozenie_y() + 1 << ")" << endl;
					break;
				}//musi znalezc jakies miejsce bo zostalo wczesniej sprawdzone w ifie
			}
		}
	}
	@Override
	public void akcja()
	{
		int ruch_x[] = { 2,-2,0,0,1,1,-1,-1 };
		int ruch_y[] = { 0,0,2,-2,1,-1,1,-1 };
		boolean wykonano = false;
		while (wykonano == false && wiek > 0)
		{
			Random rand = new Random();
			int losuj = rand.nextInt(8);
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
					else // walka
					{
						kolizja(przeciwnik);
					}
				}
				wykonano = true;
			}
		}
			wiek++;
	}
	public void wstawMlode(int x, int y, Swiat swiat)
	{
		swiat.dodajOrganizm(new Antylopa(x, y, swiat));
	}
}
