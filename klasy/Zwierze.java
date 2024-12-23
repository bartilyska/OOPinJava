import java.util.Random;

abstract public class Zwierze extends Organizm{
	
	public Zwierze(int polX, int polY, Swiat swiat) {
        super(polX, polY, swiat);
    }

	public Zwierze(int polx, int poly, int sila, int inicjatywa, char ozn, int wiek, String nazwa, Swiat swiat,boolean zyje) {
        super(polx, poly, sila, inicjatywa,ozn, wiek, nazwa, swiat,zyje);
    }
	@Override
	public void akcja()
	{
		Random rand = new Random();
		int ruch_x[] = { 1,-1,0,0 };
		int ruch_y[] = { 0,0,-1,1 };
		boolean wykonano = false;
		while (wykonano == false && wiek>0)
		{
			int losuj = rand.nextInt(4);
			int nowapoz_x = polozenie_x + ruch_x[losuj];
			int nowapoz_y = polozenie_y + ruch_y[losuj];
			if (nowapoz_x >= 0 && nowapoz_y >= 0 && nowapoz_x < swiat.getRozmiar_x() && nowapoz_y < swiat.getRozmiar_y())
			{
				Organizm przeciwnik = swiat.coStoi(nowapoz_x, nowapoz_y);
				if (przeciwnik == null) //nic nie stoi tam (lub organizm trup)
				{
					//cout << nazwa << "rusza z (" << polozenie_x + 1 << "," << polozenie_y + 1 << ")";
					swiat.usunOznaczenie(polozenie_x, polozenie_y);
					polozenie_x = nowapoz_x;
					polozenie_y = nowapoz_y;
					//cout << "i idzie na (" << nowapoz_x + 1 << "," << nowapoz_y + 1<<")" << endl;
					swiat.ustawOznaczenie(polozenie_x, polozenie_y, oznaczenie);
				}
				else
				{
					if (przeciwnik.getNazwa() == nazwa) //rozmnazanie zawsze tam bedzie ten zyjacy
					{
						if (przeciwnik.getWiek() != 0)
							rozmnazanie();
						//else
							//cout << nazwa << " (" << polozenie_x+1 << "," << polozenie_y+1 << ") " << "zakaz rozmnazania z wiekiem 0" << endl;
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
	@Override
	public void kolizja(Organizm broniacy)
	{
		if (broniacy.czyOdbilAtak(this))
		{
			//cout << broniacy->getNazwa() << " (" << broniacy->getPolozenie_x()+1<< "," <<broniacy->getPolozenie_y()+1<< ") " << "odbija atak" << nazwa
				//<< " (" << polozenie_x+1 << "," << polozenie_y+1 <<")" << endl;
			utworzLogOdbicie(broniacy);
		}
		else if (broniacy.czyMozeUciec())
		{
			broniacy.ucieczka(this);
		}
		else if (sila >= broniacy.getSila()) //atakujacy zajmie pozycje broniacego broniacy umiera (atakujacy to zawsze this)
		{
			//cout << nazwa << " (" << polozenie_x+1 << "," << polozenie_y+1 << ") " << "atakuje" << broniacy->getNazwa()
			//	<< " (" << broniacy->getPolozenie_x()+1 << "," << broniacy->getPolozenie_y()+1 << ") i go pokonuje" << endl;
			utworzLogWalkaWygrana(broniacy);
			broniacy.efektPoZjedzeniu(this);//this to atakujacy ktoremu trzeba zwiekszyc sile jak to guarana
			swiat.usunOznaczenie(polozenie_x, polozenie_y);
			polozenie_x=broniacy.getPolozenie_x();
			polozenie_y=broniacy.getPolozenie_y();
			swiat.ustawOznaczenie(polozenie_x, polozenie_y, oznaczenie); //czyli broniacego zostanie nadpisane
			swiat.usunOrganizm(broniacy);
		}
		else //broniacy wygral broniacy zostaje na swoim
		{
			utworzLogWalkaPrzegrana(broniacy);
			//cout << nazwa << " (" << polozenie_x + 1 << "," << polozenie_y + 1 << ") " << "atakuje" << broniacy->getNazwa()
				//<< " (" << broniacy->getPolozenie_x() + 1 << "," << broniacy->getPolozenie_y() + 1 << ") i od niego ginie" << endl;
			broniacy.efektPoZjedzeniu(this); //zabicie sie jagod i barszczu
			swiat.usunOznaczenie(polozenie_x, polozenie_y);
			swiat.usunOrganizm(this);
		}
	}
	@Override
	public void rozmnazanie()
	{
		int kierunekx[] = { 1,-1,0,0,1,-1,-1,1 };
		int kieruneky[] = { 0,0,1,-1,-1,1,-1,1 }; //mozliwe miejsca by wstawic
		boolean czy_rozmnozono = false;
		for (int i = 0; i < 8; i++)
		{
			int dziecko_x = polozenie_x + kierunekx[i];
			int dziecko_y = polozenie_y + kieruneky[i];
			if (dziecko_x >= 0 && dziecko_y >= 0 && dziecko_x < swiat.getRozmiar_x() && dziecko_y < swiat.getRozmiar_y()
				&& swiat.coStoi(dziecko_x, dziecko_y)==null) //mozna wtedy postawic
			{
				wstawMlode(dziecko_x, dziecko_y,swiat);
				swiat.ustawOznaczenie(dziecko_x, dziecko_y, oznaczenie);
				czy_rozmnozono = true;
				//cout << nazwa << " (" << polozenie_x+1 << "," << polozenie_y+1 << ") " << "rozmnozyl sie" << endl;
				utworzLogRozmnozenie();
				break;
			}
		}
		//if (czy_rozmnozono == 0)
			//cout <<nazwa<<" ("<<polozenie_x+1<<","<<polozenie_y+1<< ") " << "probowal sie rozmnozyc ale nie ma miejsca"<< endl;
	}
	public void utworzLogOdbicie(Organizm broniacy)
	{
		String log = broniacy.getNazwa() + " (" + (broniacy.getPolozenie_x() + 1) + "," +  (broniacy.getPolozenie_y() + 1) +
			") odbija atak " + nazwa +"(" + (polozenie_x + 1) + "," + (polozenie_y + 1) + ")";
		swiat.dodajLog(log);
	}
	public void utworzLogWalkaWygrana(Organizm broniacy)
	{
		String log = nazwa + " (" + (polozenie_x + 1) + "," + (polozenie_y + 1) + ") atakuje " + broniacy.getNazwa() +
			" (" + (broniacy.getPolozenie_x()+1) + "," + (broniacy.getPolozenie_y()+1) + ") i wygrywa";
		swiat.dodajLog(log);
	}
	public void  utworzLogWalkaPrzegrana(Organizm broniacy)
	{
		String log = nazwa + " (" + (polozenie_x + 1) + "," + (polozenie_y + 1) + ") atakuje " + broniacy.getNazwa() +
			" (" + (broniacy.getPolozenie_x()+1) + "," + (broniacy.getPolozenie_y()+1) + ") i od niego ginie";
		swiat.dodajLog(log);
	}
	public void  utworzLogRozmnozenie()
	{
		String log = nazwa + " (" + (polozenie_x + 1) + "," + (polozenie_y + 1) + ") rozmnozyl sie";
		swiat.dodajLog(log);
	}
	public void  utworzLogUcieczka(Organizm atakujacy)
	{
		String log = nazwa + " uciekla na (" + (polozenie_x + 1) + "," + (polozenie_y + 1) + ") przed " + atakujacy.getNazwa() +
			" z ("+(atakujacy.getPolozenie_x()+1)+","+(atakujacy.getPolozenie_y()+1)+")";
		swiat.dodajLog(log);
	}
}
