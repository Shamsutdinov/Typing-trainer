package Typing_test;

public class TypeSpeedCaster {

    private int typed_symbols, mistake;
    private float time;
    private boolean started;

    public TypeSpeedCaster() {

    }

    public void setTyped_symbols(int typed_symbols) {
        this.typed_symbols = typed_symbols;
    }

    public void typedCorrect() {
        typed_symbols++;
    }

    public int getTyped_symbols() {
        return typed_symbols;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public float getSpeed() {
        return typed_symbols / (time / 60);
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean status) {
        started = status;
    }

    public int getMistake() {
        return mistake;
    }

    public void typedMistake() {
        mistake++;
    }

    public void setMistake(int mistake) {
        this.mistake = mistake;
    }

    public void reset() {
        time = 0;
        typed_symbols = 0;
        mistake = 0;
    }

}
