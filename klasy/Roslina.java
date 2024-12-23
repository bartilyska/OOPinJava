import java.util.Random;

abstract public class Roslina extends Organizm{
	
	protected int szansa_zasiania;
	
	public Roslina(int polX, int polY, Swiat swiat) {
        super(polX, polY, swiat);
    }

	public Roslina(int polx, int poly, int sila, int inicjatywa, char ozn, int wiek, String nazwa, Swiat swiat,boolean zyje) {
        super(polx, poly, sila, inicjatywa,ozn, wiek, nazwa, swiat,zyje);
    }
	public void akcja()
	{
		Random rand = new Random();
		int losuj = rand.nextInt(100);
		if (losuj < szansa_zasiania)
		{
			int ruch_x[] = { 1,-1,0,0 };
			int ruch_y[] = { 0,0,-1,1 };
			int sprkierunki[] = {0,0,0,0};
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
					break;
				}
				
			}
		}
		wiek++;
	}
	public void kolizja(Organizm broniacy)
	{
	}
	public void utworzLogRozsianie(int nowapoz_x,int nowapoz_y)
	{
		String log= nazwa + " rozsiewa sie z (" + (polozenie_x + 1) + "," + (polozenie_y + 1) + ")"+ " na (" +
			(nowapoz_x + 1) + "," + (nowapoz_y + 1) + ")";
		swiat.dodajLog(log);
	}
	public void utworzLogAtak(Organizm przeciwnik)
	{
		String log= nazwa+" ("+(polozenie_x+1) + ","+ (polozenie_y + 1) + ") zabija " + przeciwnik.getNazwa() +
			" (" + (przeciwnik.getPolozenie_x()+1)+","+ (przeciwnik.getPolozenie_y()+1)+")";
		swiat.dodajLog(log);
	}
}
