package com.lightsaway.raml;

import org.apache.commons.collections.keyvalue.MultiKey;
import org.apache.commons.collections.map.MultiKeyMap;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * Reporter for rest testing
 */
public class CoverageAndStatsReporter {
    private static final int path_index = 0, method_index = 1, response_index = 2;

    private MultiKeyMap map;
    private int totalAmountOfRequests = 0;

    public CoverageAndStatsReporter(MultiKeyMap data){
        this.map = data;
        map.forEach( (key,value) -> totalAmountOfRequests += (int) value);
    }

    /**
     * Saves statistics to html
     * @param pathToSave Path to file where report will be saved. File name should be included
     * @param reportName Name of report that will be embedded in html heading
     * @param cssFilePath Optional value, can be either http or local. If local css will be inlined
     */
    public void saveHtmlReport(String pathToSave, String reportName, String cssFilePath){

        StringBuilder builder = new StringBuilder();
        builder.append("<html>").append("<head>");

        if(!isEmpty(cssFilePath) && cssFilePath.contains("http")){
            builder.append("<link rel=\"stylesheet\" href=\"" + cssFilePath + "\">");
        }
        else if (!isEmpty(cssFilePath)){
            builder.append("<style>" + readFile(cssFilePath) + "</style>");
        }

        builder.append("</head>").append("<body>");

        if(reportName != null){
            builder.append("<h1>").append(reportName).append("</h1>");
        }

        builder.append("<table>")
                .append("<tr>")
                .append("<th>PATH</th><th>METHOD</th><th>RESPONSE</th><th>COUNT</th>")
                .append("</tr>");

        map.keySet().forEach(key -> {
                    builder.append("<tr>")
                            .append("<td>" + ((MultiKey) key).getKey(path_index) + "</td>")
                            .append("<td>" + ((MultiKey) key).getKey(method_index) + "</td>")
                            .append("<td>" + ((MultiKey) key).getKey(response_index) + "</td>")
                            .append("<td>" + map.get(((MultiKey) key).getKey(0), ((MultiKey) key).getKey(1), ((MultiKey) key).getKey(2)) + "</td>")
                            .append("</tr>");
                }
        );

        builder.append("<tfoot><tr><td></td><td></td><td>TOTAL</td><td>" + totalAmountOfRequests + "</td></tr></tfoot>")
                .append("</table>")
                .append("</body>");

        if(this.map.containsValue(0)){
                //This is because lambda allows only finals
                final int[] covered = {0};
                map.forEach((k,v) -> {if((int)v > 0) covered[0] +=1;});
                float percentage = covered[0] *100f / map.size();
                builder.append("<footer> <h2>Coverage : " + percentage + "% </h2></footer>");
        }

        builder.append("</html>");
        String out = builder.toString();

        try {
            FileUtils.writeStringToFile(new File(pathToSave), out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JSONObject toJson(){
        JSONObject json = new JSONObject();
        throw new NotImplementedException();
    }

    private static String readFile(String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            return "";
        }
    }


}
