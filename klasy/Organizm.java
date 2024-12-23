
abstract public class Organizm {
	protected int polozenie_x,polozenie_y,wiek,sila,inicjatywa;
	protected char oznaczenie;
	protected String nazwa;
	protected Swiat swiat;
	protected boolean zyje;
	abstract public void akcja();
	abstract public void kolizja(Organizm broniacy);
	public boolean czyOdbilAtak(Organizm atakujacy)
	{
		return false;
	}
	public boolean czyMozeUciec()
	{
		return false;
	}
	public void efektPoZjedzeniu(Organizm atakujacy) {}
	public void rozmnazanie() {}
	public void ucieczka(Organizm  atakujacy) {}
	abstract public void wstawMlode(int x, int y, Swiat swiat);
    
	public Organizm(int polx, int poly, Swiat swiat)
    {
        this.polozenie_x = polx;
        this.polozenie_y = poly;
        this.swiat = swiat;
        this.zyje=true;
    }
	public Organizm(int polx, int poly, int sila, int inicjatywa, char ozn, int wiek, String nazwa, Swiat swiat,boolean zyje)
    {
        this.polozenie_x = polx;
        this.polozenie_y = poly;
        this.wiek = wiek;
        this.inicjatywa = inicjatywa;
        this.oznaczenie = ozn;
        this.sila = sila;
        this.nazwa = nazwa;
        this.swiat = swiat;
        this.zyje=zyje;
    }
	public int getPolozenie_x()
	{
		return polozenie_x;
	}
	public int getPolozenie_y()
	{
		return polozenie_y;
	}
	public void setPolozenie_x(int pozx)
	{
		polozenie_x = pozx;
	}
	public void setPolozenie_y(int pozy)
	{
		polozenie_y = pozy;
	}
	public int getWiek()
	{
		return wiek;
	}
	public String getNazwa()
	{
		return nazwa;
	}
	public int getInicjatywa()
	{
		return inicjatywa;
	}
	public int getSila()
	{
		return sila;
	}
	public void setSila(int nowasila)
	{
		sila = nowasila;
	}
	public boolean getZyje()
	{
		return zyje;
	}
	public void setZyje(boolean zycie)
	{
		zyje = zycie;
	}
	public char getOznacznie()
	{
		return oznaczenie;
	}
	public void wypiszOrganizm()
	{
		System.out.println(nazwa + " " + (polozenie_x+1) + " " + (polozenie_y+1) + " " + wiek + " " + sila + " " + inicjatywa + " " + oznaczenie+" "+zyje);
	}
}
