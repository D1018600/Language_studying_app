package fcu.app.language_studying_app;

public class Stage {
    private String id;
    private int code;
    private String name;
    private String sentence;
    private String word1;
    private String word2;
    private String word3;
    private String word4;
    private String word5;
    private String word6;
    private String word7;
    private String word8;
    private String chinese;
    public Stage(){

    }

    public Stage(int code, String name, String sentence,String chinese, String word1, String word2, String word3, String word4, String word5, String word6, String word7, String word8) {
        this.name = name;
        this.sentence = sentence;
        this.word1 = word1;
        this.word2 = word2;
        this.word3 = word3;
        this.word4 = word4;
        this.word5 = word5;
        this.word6 = word6;
        this.code = code;
        this.word7 = word7;
        this.word8 = word8;
        this.chinese = chinese;
    }

    // Getter and Setter methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public String getWord1() {
        return word1;
    }

    public void setWord1(String word1) {
        this.word1 = word1;
    }

    public String getWord2() {
        return word2;
    }

    public void setWord2(String word2) {
        this.word2 = word2;
    }

    public String getWord3() {
        return word3;
    }

    public void setWord3(String word3) {
        this.word3 = word3;
    }

    public String getWord4() {
        return word4;
    }

    public void setWord4(String word4) {
        this.word4 = word4;
    }

    public String getWord5() {
        return word5;
    }

    public void setWord5(String word5) {
        this.word5 = word5;
    }

    public String getWord6() {
        return word6;
    }

    public void setWord6(String word6) {
        this.word6 = word6;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getWord7() {
        return word7;
    }

    public void setWord7(String word7) {
        this.word7 = word7;
    }

    public String getWord8() {
        return word8;
    }

    public void setWord8(String word8) {
        this.word8 = word8;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }
}
