package ru.daemon.colorization.game.turns;

import com.badlogic.gdx.scenes.scene2d.Action;

import java.util.ArrayList;
import java.util.List;

public class TurnManager extends Action {
    public static final float TURN_DURATION = 0.4f;

    private List<TurnHandler> beforeTurnHandlers = new ArrayList<>();
    private List<TurnHandler> afterTurnHandlers = new ArrayList<>();
    private List<TurnCommand> commands = new ArrayList<>();
    private boolean turnRunning;
    private float time;
    private long turnCount;

    public void turn() {
        if (turnRunning) return;

        turnCount++;
        turnRunning = true;
        beforeTurnHandlers.forEach(TurnHandler::handleTurn);
        commands.forEach(TurnCommand::execute);
    }

    private void endTurn(){
        turnRunning = false;
        commands.clear();
        afterTurnHandlers.forEach(TurnHandler::handleTurn);
    }

    public void addBeforeTurnHandler(TurnHandler handler){
        beforeTurnHandlers.add(handler);
    }

    public void addAfterTurnHandler(TurnHandler handler){
        afterTurnHandlers.add(handler);
    }

    public void addCommand(TurnCommand command){
        commands.add(command);
    }

    public long getTurnCount() {
        return turnCount;
    }

    @Override
    public boolean act(float delta) {
        if (!turnRunning) return false;

        time += delta;
        if (time < TURN_DURATION) return false;

        time = 0;
        endTurn();
        return false;
    }
}

