package gameLogic.resource;

public class Train extends Resource {
    private String image;

    public Train(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getImage() {
        return image;
    }
}
