package player;

import common.Mark;

public interface Player {
    
    void play();

    void setMark(Mark mark);

    Mark getMark();

    void setName(String name);

    String getName();
}
