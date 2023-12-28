public class Hotel {
    private String name;
    private String location;
    private float rating;

    public Hotel(String name, String location, float rating) {
        this.name = name;
        this.location = location;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }
    @Override
    public String toString(){
        return this.name +" (" + this.location + ", "+this.rating+")";
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
