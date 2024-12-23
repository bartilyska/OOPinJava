
public class Czlowiek extends Zwierze{
	private int ile_jeszcze_mocy;
	
	public Czlowiek(int polX, int polY, Swiat swiat)
	{
        super(polX, polY, swiat);
        wiek = 0;
        sila = 5;
        inicjatywa = 4;
        oznaczenie = 'C';
        nazwa = "Czlowiek";
    }

	public Czlowiek(int polx, int poly, int sila, int inicjatywa, char ozn, int wiek, String nazwa, Swiat swiat,boolean zyje,int ile_jeszcze) {
        super(polx, poly, sila, inicjatywa,ozn, wiek, nazwa, swiat,zyje);
        ile_jeszcze_mocy = ile_jeszcze;
    }
	public void akcja()
	{
		String kierunek = swiat.getKierunekCzlowieka();
		specjalnaUmiejetnosc();
		int ruch_x=0, ruch_y=0;
		if (kierunek == "gora")
		{
			ruch_x = 0;
			ruch_y = -1;
		}
		else if (kierunek == "dol")
		{
			ruch_x = 0;
			ruch_y = 1;
		}
		else if (kierunek == "lewo")
		{
			ruch_x = -1;
			ruch_y = 0;
		}
		else if (kierunek == "prawo")
		{
			ruch_x = 1;
			ruch_y = 0;
		}
		int nowapoz_x = polozenie_x + ruch_x;
		int nowapoz_y = polozenie_y + ruch_y;
		if (nowapoz_x >= 0 && nowapoz_y >= 0 && nowapoz_x < swiat.getRozmiar_x() && nowapoz_y < swiat.getRozmiar_y() && ruch_x!=ruch_y)
		{ //jezeli zostan na miejscu to tu nie wchodz by nie wywolac kolizji
			Organizm przeciwnik = swiat.coStoi(nowapoz_x, nowapoz_y);
			if (przeciwnik == null) //nic nie stoi tam (lub organizm trup)
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
				kolizja(przeciwnik);
				/*if (przeciwnik->getNazwa() == nazwa) //rozmnazanie zawsze tam bedzie ten zyjacy
				{
					if (przeciwnik->getWiek() != 0)
						rozmnazanie();
					else
						cout << nazwa << " (" << polozenie_x + 1 << "," << polozenie_y + 1 << ") " << "zakaz rozmnazania z wiekiem 0" << endl;
				}*/
				 // walka
					
			}
		}
		wiek++;
	}
	public int getIleJeszczeMocy()
	{
		return ile_jeszcze_mocy;
	}
	public void specjalnaUmiejetnosc()
	{ 
		if (ile_jeszcze_mocy == 0 && swiat.getCooldownMoc() == 10)//jezeli cooldown 10 to znaczy ze odpalono moc
		{
			sila = 10;
			ile_jeszcze_mocy = 5;
		}
		else if (ile_jeszcze_mocy > 0)
		{
			sila--;
			ile_jeszcze_mocy--;
		}
		if (swiat.getCooldownMoc() > 0)
			swiat.setCooldownMoc(swiat.getCooldownMoc() - 1);

	}
	@Override
	public void wstawMlode(int x, int y, Swiat swiat) {
		swiat.dodajOrganizm(new Czlowiek(x, y, swiat));
	}
}
