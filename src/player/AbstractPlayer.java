package player;

import common.Mark;
import model.HashTagModel;

public abstract class AbstractPlayer implements Player {

    protected Mark myMark = null;
    protected Mark opponentMark = null;

    protected final HashTagModel hashTag;

    protected String name = "";

    protected AbstractPlayer(HashTagModel hashTag) {
        this.hashTag = hashTag;
    }

    @Override
    public final void setMark(Mark mark) {
        this.myMark = mark;
        this.opponentMark = (mark == Mark.X ? Mark.O : Mark.X);
    }

    @Override
    public final Mark getMark() {
        return myMark;
    }

    @Override
    public final void setName(String name) {
        this.name = name;
    }

    @Override
    public final String getName() {
        return this.name;
    }

    @Override
    public abstract void play();
}
