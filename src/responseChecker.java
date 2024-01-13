public class responseChecker {
    private String response;
    public responseChecker(String response) {
        this.response = response;
    }
    public String[] getResponse() {
        String[] currentWeather = new String[5];
        String Word = "";
        String name = "";
        String time = "";
        String temperature = "";
        String condition = "";
        String conditionImage = "";
        boolean checkingWord = false;
        boolean checkingName = false;
        boolean checkingTime = false;
        boolean checkingTemperature = false;
        boolean checkingCondition = false;
        boolean checkingCondtionImage = false;
        for (int i = 0; i < response.length(); i++){
            if (response.charAt(i) == '"' && checkingWord){
                if (Word.equals("name")){
                    checkingName = true;
                    checkingWord = false;
                    i+=3;
                } else if (Word.equals("localtime")){
                    checkingTime = true;
                    checkingWord = false;
                    i+=14;
                } else if (Word.equals("temp_f")){
                    checkingTemperature = true;
                    checkingWord = false;
                    i+=2;
                } else if (Word.equals("text")){
                    checkingCondition = true;
                    checkingWord = false;
                    i+=3;
                } else if (Word.equals("icon")){
                    checkingCondtionImage = true;
                    checkingWord = false;
                    i+=5;
                } else {
                    Word = "";
                }
            } else if (response.charAt(i) == '"' && !checkingWord) {
                checkingWord = true;
            } else if (checkingWord && response.charAt(i) != ':' && response.charAt(i) != '{'
                    && response.charAt(i) != '}' && response.charAt(i) != ',') {
                Word = Word + response.charAt(i);
            }
            if (checkingName && response.charAt(i)=='"'){
                checkingName = false;
                checkingWord = true;
                currentWeather[0] = name;
                Word = "";
            } else if (checkingName) {
                name = name + response.charAt(i);
            }
            if (checkingTime && response.charAt(i)=='"'){
                checkingTime = false;
                checkingWord = true;
                currentWeather[1] = time;
                Word = "";
            } else if (checkingTime) {
                time = time + response.charAt(i);
            }
            if (checkingTemperature && response.charAt(i)==','){
                checkingTemperature = false;
                checkingWord = true;
                currentWeather[2] = temperature;
                Word = "";
            } else if (checkingTemperature) {
                temperature = temperature + response.charAt(i);
            }
            if (checkingCondition && response.charAt(i)=='"'){
                checkingCondition = false;
                checkingWord = true;
                i++;
                currentWeather[3] = condition;
                Word = "";
            } else if (checkingCondition) {
                condition = condition + response.charAt(i);
            }
            if (checkingCondtionImage && response.charAt(i)=='"'){
                checkingCondtionImage = false;
                i++;
                currentWeather[4] = conditionImage;
                break;
            } else if (checkingCondtionImage) {
                conditionImage = conditionImage + response.charAt(i);
            }
        }

        return currentWeather;
    }
}
