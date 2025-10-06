public class ModelClient
{
    private String Name;
    private int amount;
    private long id;

    public ModelClient(String Name) {
        this.Name = Name;
        this.amount = 0;
        this.id=0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ModelClient(String Name, int amount, long id) {
        this.Name = Name;
        this.amount = amount;
        this.id = id;
    }

    public void setamount(int s)
    {
        this.amount = s;
    }
    public String getName() {
        return Name;
    }

    public int getamount() {
        return amount;

    }





}