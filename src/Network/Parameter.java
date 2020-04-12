package Network;

public class Parameter {
    public String key;
    public String value;

    public static Parameter[] parse(String message) {
        String[] rawParameters = message.split(";");
        Parameter[] parameters = new Parameter[rawParameters.length];
        for (int i = 0; i < rawParameters.length; i++) {
            String[] keyValue = rawParameters[i].split("=");
            parameters[i] = new Parameter();
            parameters[i].key = keyValue[0];
            parameters[i].value = keyValue[1];
        }
        return parameters;
    }
}
