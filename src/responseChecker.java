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
        boolean checkingRegion = false;
        boolean checkingName = false;
        boolean checkingTime = false;
        boolean checkingTemperature = false;
        boolean checkingFeelsLike = false;
        boolean checkingCondition = false;
        boolean checkingCondtionImage = false;
        boolean checkingWind = false;
        System.out.println(response);
        for (int i = 0; i < response.length(); i++){
            if (response.charAt(i) == '"' && checkingWord){
                if (Word.equals("name")){
                    checkingName = true;
                    checkingWord = false;
                    i+=3;
                } else if (Word.equals("region")) {
                    checkingWord = false;
                    checkingRegion = true;
                    currentWeather[0] += ", ";
                    i+=3;

                } else if (Word.equals("localtime")){
                    checkingTime = true;
                    checkingWord = false;
                    i+=14;
                } else if (Word.equals("temp_f")){
                    checkingTemperature = true;
                    checkingWord = false;
                    i+=2;
                } else if (Word.equals("feelslike_f")){
                    checkingFeelsLike = true;
                    checkingWord = false;
                    currentWeather[2] += "°F, Feels Like: ";
                    i+=2;
                }
                else if (Word.equals("text")){
                    checkingCondition = true;
                    checkingWord = false;
                    i+=3;
                } else if (Word.equals("icon")){
                    checkingCondtionImage = true;
                    checkingWord = false;
                    i+=5;
                } else if (Word.equals("wind_mph")) {
                    checkingWind = true;
                    checkingWord = false;
                    currentWeather[3]+= ", Wind(MPH): ";
                    i+=2;
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
            if (checkingRegion && response.charAt(i) == '"'){
                checkingRegion = false;
                checkingWord = true;
                Word = "";
            }
            else if (checkingRegion){
                currentWeather[0] += response.charAt(i);
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
            if (checkingFeelsLike && response.charAt(i)=='"'){
                currentWeather[2] = currentWeather[2].substring(0,currentWeather[2].length()-1);
                checkingFeelsLike = false;
                checkingWord = true;
                Word = "";
            } else if (checkingFeelsLike){
                currentWeather[2] += response.charAt(i);
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
                currentWeather[4] = conditionImage;
                checkingCondtionImage = false;
                Word = "";
            } else if (checkingCondtionImage) {
                conditionImage = conditionImage + response.charAt(i);
            }
            if (checkingWind && response.charAt(i)==','){
                checkingWord = true;
                checkingWind = false;
                Word = "";
            } else if (checkingWind) {
                currentWeather[3] += response.charAt(i);
            }
        }

        return currentWeather;
    }
}
