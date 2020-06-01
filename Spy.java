public class Spy {
    private int spyIndexX;
    private int spyIndexY;
    Spy()
    {
        spyIndexX=0;
        spyIndexY=0;
    }

    public void SetX(int x){
        spyIndexX= x;
    }
    public void SetY(int y){
        spyIndexY= y;
    }
    public int GetX(){
        return spyIndexX;
    }
    public int GetY(){
        return spyIndexY;
    }
}