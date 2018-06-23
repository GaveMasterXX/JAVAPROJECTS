package pt.ipbeja.po2.sokoban.model;

/**
 * Creaed by Tiago Campos Nº 13953
 * 6/12/2018
 */

public class Score {

    private  int points;
    private  String keeperName;
    private  String levalName;

    public Score(String keeperName, String levalName, int points) {
        this.keeperName = keeperName;
        this.levalName = levalName;
        this.points = points;
    }

    public  int getPoints() {
        return points;
    }

    public  String getKeeperName() {
        return keeperName;
    }

    public  String getLevalName() {
        return levalName;
    }


}
