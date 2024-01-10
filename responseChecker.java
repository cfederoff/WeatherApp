public class responseChecker {
    private String response;
    public responseChecker(String response) {
        this.response = response;
    }
    public String[] getResponse() {
        String[] currentWeather = new String[3];
        String Word = "";
        String name = "";
        String temperature = "";
        String condition = "";
        boolean checkingWord = false;
        boolean checkingName = false;
        boolean checkingTemperature = false;
        boolean checkingCondition = false;
        for (int i = 0; i < response.length(); i++){
            if (response.charAt(i) == '"' && checkingWord){
                if (Word.equals("name")){
                    checkingName = true;
                    checkingWord = false;
                    i+=3;
                } else if (Word.equals("temp_f")){
                    checkingTemperature = true;
                    checkingWord = false;
                    i+=2;
                } else if (Word.equals("text")){
                    checkingCondition = true;
                    checkingWord = false;
                    i+=3;
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
            if (checkingTemperature && response.charAt(i)==','){
                checkingTemperature = false;
                checkingWord = true;
                currentWeather[1] = temperature;
                Word = "";
            } else if (checkingTemperature) {
                temperature = temperature + response.charAt(i);
            }
            if (checkingCondition && response.charAt(i)=='"'){
                checkingCondition = false;
                i++;
                currentWeather[2] = condition;
                break;
            } else if (checkingCondition) {
                condition = condition + response.charAt(i);
            }
        }

        return currentWeather;
    }
}
