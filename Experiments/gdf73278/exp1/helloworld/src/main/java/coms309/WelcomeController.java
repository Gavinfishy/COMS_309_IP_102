package coms309;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;


@RestController
class WelcomeController {

    @GetMapping("/")
    public String welcome() {
        return "Hello and welcome to COMS 309";
    }

    @GetMapping("/name/{name}")
    public String welcome(@PathVariable String name) {
        return "Hello and welcome to COMS 309: " + name;
    }

    @GetMapping("/intro")
    public String intro() {
        return "You found part of my first experiment";
    }


    @GetMapping("/fav_color/{color}")
    public String color(@PathVariable String color) {
        String html = "<html><head><style>body { background-color: " + color + "; }</style></head><body>";
        html += "<h1>Your favorite color: " + color + "</h1>";
        html += "</body></html>";
        return "Your fav color: " + color + html;
    }

    @GetMapping("/json_example")
    public String jsonExampleGet() {
        JSONObject json = new JSONObject("{\n" +
                "  \"object\": {\n" +
                "    \"string\": \"Hello World!\",\n" +
                "    \"array\": [0, 1, 2, 3],\n" +
                "    \"boolean\": true,\n" +
                "    \"int\": 1,\n" +
                "    \"double\": 1.92\n" +
                "  }\n" +
                "}");

        return json.toString();
    }

    @PostMapping("/json_example")
    public String jsonExamplePost(@RequestBody JSONExample json) {
        System.out.println(json.getDoubleNum());
        System.out.println(json.getNumber());
        System.out.println(json.getString());
        System.out.println(json.getArray());
        return (new JSONObject(json)).toString();
    }

}
