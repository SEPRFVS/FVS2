package gameLogic.map;

    abstract public class IPositionable {

        public abstract int getX();

        public abstract int getY();

        public abstract void setX(int x);

        public abstract void setY(int y);

        public abstract boolean equals(Object o);
}