package app.example;


public class ExampleDocHtml {
    
    public static final String htmlExample="<!DOCTYPE html>\n" + 
                "<html>\n" + 
                "    <head>\n" + 
                "        <meta charset=\"UTF-8\" /> \n" + 
                "        <title>API Documentation</title>\n" + 
                "            \n" + 
                "        <style>\n" + 
                "            \n" + 
                "            .column_center{\n" + 
                "                display: flex;\n" + 
                "                align-items: center;\n" + 
                "                justify-content: center;\n" + 
                "                width: 100%;\n" + 
                "                flex-direction: column;\n" + 
                "            }\n" + 
                "            \n" + 
                "            .nav{\n" + 
                "                position: fixed;\n" + 
                "                inset-inline-start: 0px;\n" + 
                "                inset-block-start: 0px;\n" + 
                "                background: rgb(26, 26, 26);\n" + 
                "                height: 100vh;\n" + 
                "                width: 13vw;\n" + 
                "                box-shadow: 2px 2px 2px rgb(121, 121, 121);\n" + 
                "                padding-top: 60px;\n" + 
                "            }\n" + 
                "            \n" + 
                "            .nav_item_activated{\n" + 
                "                text-align: center;\n" + 
                "                font: 20px \"Times New Roman\", Serif;\n" + 
                "                cursor: pointer;\n" + 
                "                color: rgb(255, 255, 255);\n" + 
                "                margin: 25px 0px 25px 0px;\n" + 
                "                text-decoration: none;\n" + 
                "                display: block;\n" + 
                "            }\n" + 
                "            \n" + 
                "            .nav_item_desactivated{\n" + 
                "                text-align: center;\n" + 
                "                font: 20px \"Times New Roman\", Serif;\n" + 
                "                cursor: pointer;\n" + 
                "                color: rgb(133, 133, 133);\n" + 
                "                margin: 25px 0px 25px 0px;\n" + 
                "                text-decoration: none;\n" + 
                "                display: block;\n" + 
                "            }\n" + 
                "            \n" + 
                "            .column_left{\n" + 
                "                display: flex;\n" + 
                "                align-items: left;\n" + 
                "                justify-content: center;\n" + 
                "                width: 100%;\n" + 
                "                flex-direction: column;\n" + 
                "            }\n" + 
                "            \n" + 
                "            .line{\n" + 
                "                display: flex;\n" + 
                "                align-items: center;\n" + 
                "                justify-content: flex-start;\n" + 
                "                width: 100%;\n" + 
                "                flex-direction: row;\n" + 
                "            }\n" + 
                "            \n" + 
                "            .inline{\n" + 
                "                display : inline-block;\n" + 
                "            }\n" + 
                "            \n" + 
                "            .margin1{\n" + 
                "                margin: 100px 0px 0px 20vw;\n" + 
                "            }\n" + 
                "            \n" + 
                "            .margin2{\n" + 
                "                margin: 70px 0px 70px 0px;\n" + 
                "            }\n" + 
                "            \n" + 
                "            .margin3{\n" + 
                "                margin: 40px 0px 0px 50px;\n" + 
                "            }\n" + 
                "            \n" + 
                "            .margin4{\n" + 
                "                margin: 40px 0px 0px 0px;\n" + 
                "            }\n" + 
                "            \n" + 
                "            .margin5{\n" + 
                "                margin: 70px 0px 0px 20vw;\n" + 
                "            }\n" + 
                "            \n" + 
                "            .max_width_1{\n" + 
                "                max-width: 400px;\n" + 
                "            }\n" + 
                "            \n" + 
                "            .indented{\n" + 
                "                margin-left: 40px;\n" + 
                "            }\n" + 
                "            \n" + 
                "            .demi_indented{\n" + 
                "                margin-left: 20px;\n" + 
                "            }\n" + 
                "            \n" + 
                "            .bgreen{\n" + 
                "                background-color: rgb(25, 206, 25);\n" + 
                "            }\n" + 
                "            \n" + 
                "            .bblue{\n" + 
                "                background-color: rgb(52, 110, 175);\n" + 
                "            }\n" + 
                "            \n" + 
                "            .blightblue{\n" + 
                "                background-color: rgb(73, 156, 233);\n" + 
                "            }\n" + 
                "            \n" + 
                "            .bred{\n" + 
                "                background-color: rgb(230, 28, 28);\n" + 
                "            }\n" + 
                "            \n" + 
                "            .borange{\n" + 
                "                background-color: rgb(250, 123, 20);\n" + 
                "            }\n" + 
                "            \n" + 
                "            .bpurple{\n" + 
                "                background-color: rgb(173, 74, 231);\n" + 
                "            }\n" + 
                "            \n" + 
                "            .method_badge{\n" + 
                "                padding: 6px 8px 6px 8px;\n" + 
                "                margin: 5px 0px 5px 0px;\n" + 
                "                border-radius: 8px 8px 8px 8px;\n" + 
                "                color: rgb(255,255,255);\n" + 
                "                font: bold 21px \"Times New Roman\", Serif;\n" + 
                "                display: inline-block;\n" + 
                "                box-shadow: 2px 2px 3px rgb(0,0,0);\n" + 
                "            }\n" + 
                "            \n" + 
                "            .status_badge{\n" + 
                "                padding: 5px 6px 5px 6px;\n" + 
                "                margin: 5px 7px 5px 7px;\n" + 
                "                border-radius: 8px 8px 8px 8px;\n" + 
                "                color: rgb(255,255,255);\n" + 
                "                font: bold 17px \"Times New Roman\", Serif;\n" + 
                "                display: inline-block;\n" + 
                "                \n" + 
                "            }\n" + 
                "            \n" + 
                "            .title{\n" + 
                "                text-align: left;\n" + 
                "                display: block;\n" + 
                "                font: bold 45px \"Times New Roman\", Serif;\n" + 
                "                margin: 0px 0px 5px 0px;\n" + 
                "                color: rgb(250, 123, 20);\n" + 
                "            }\n" + 
                "            \n" + 
                "            .text1{\n" + 
                "                text-align: left;\n" + 
                "                font: 20px \"Times New Roman\", Serif;\n" + 
                "                margin-top: 5px;\n" + 
                "                margin-bottom: 5px;\n" + 
                "            }\n" + 
                "            \n" + 
                "            .text2{\n" + 
                "                text-align: left;\n" + 
                "                font: 23px \"Times New Roman\", Serif;\n" + 
                "                margin-top: 25px;\n" + 
                "                margin-bottom: 8px;\n" + 
                "            }\n" + 
                "            \n" + 
                "            .text3{\n" + 
                "                text-align: left;\n" + 
                "                font: 17px \"Times New Roman\", Serif;\n" + 
                "                margin-top: 5px;\n" + 
                "                margin-bottom: 5px;\n" + 
                "            }\n" + 
                "            \n" + 
                "            .text4{\n" + 
                "                text-align: left;\n" + 
                "                font: bold 36px \"Times New Roman\", Serif;\n" + 
                "                margin-top: 5px;\n" + 
                "                margin-bottom: 5px;\n" + 
                "            }\n" + 
                "            \n" + 
                "            .tab_activated{\n" + 
                "                box-shadow: 3px 0px 2px rgb(117, 117, 117);\n" + 
                "                margin: 0px 8px 0px 0px; \n" + 
                "                border: 0px solid rgb(71, 71, 71);\n" + 
                "                padding: 6px 8px 6px 8px;\n" + 
                "                cursor: default;\n" + 
                "                display: inline-block;\n" + 
                "                background-image : linear-gradient(180deg, rgb(21, 28, 80), rgb(70, 92, 221));\n" + 
                "                text-align: center;\n" + 
                "                border-radius: 6px 15px 0px 0px;\n" + 
                "                font: 14px \"Times New Roman\", Serif;\n" + 
                "                width: 140px;\n" + 
                "                color: rgb(255,255,255);\n" + 
                "            }\n" + 
                "            \n" + 
                "            .tab_desactivated{\n" + 
                "                box-shadow: 3px 0px 2px rgb(117, 117, 117);\n" + 
                "                margin: 0px 8px 0px 0px; \n" + 
                "                border: 0px solid rgb(71, 71, 71);\n" + 
                "                padding: 6px 8px 6px 8px;\n" + 
                "                cursor: default;\n" + 
                "                display: inline-block;\n" + 
                "                background-image : linear-gradient(180deg, rgb(19, 19, 19), rgb(116, 116, 116));\n" + 
                "                text-align: center;\n" + 
                "                border-radius: 6px 15px 0px 0px;\n" + 
                "                font: 14px \"Times New Roman\", Serif;\n" + 
                "                width: 140px;\n" + 
                "                color: rgb(255,255,255);\n" + 
                "            }\n" + 
                "            \n" + 
                "            .tab_div{\n" + 
                "                overflow: auto;\n" + 
                "                scrollbar-width: auto;\n" + 
                "                max-height: 200px;\n" + 
                "                border-radius: 0px 12px 12px 12px;\n" + 
                "                border: 3px solid rgb(70, 92, 221);\n" + 
                "                box-shadow: 3px 2px 2px rgb(117, 117, 117);\n" + 
                "                background-color: rgb(32, 32, 32);\n" + 
                "                width: 500px;\n" + 
                "            }\n" + 
                "            \n" + 
                "            .tab_text{\n" + 
                "                margin: 0px 8px 10px 0px;\n" + 
                "                padding: 9px 12px 9px 12px;\n" + 
                "                font: 15px \"Times New Roman\", Serif;\n" + 
                "                color: rgb(255, 255, 255);\n" + 
                "                height: 160px;\n" + 
                "            }\n" + 
                "        </style>\n" + 
                "            \n" + 
                "        <script>\n" + 
                "            \n" + 
                "            let nav_items;\n" + 
                "            let screens;\n" + 
                "            \n" + 
                "            nav_items = [\"ref1\"];\n" + 
                "            \n" + 
                "            screens = [];\n" + 
                "            \n" + 
                "            \n" + 
                "            function setup(){\n" + 
                "            \n" + 
                "                for(let nav_item of nav_items){\n" + 
                "                    let node = document.getElementById(nav_item);\n" + 
                "                    \n" + 
                "                    node.addEventListener('mouseover',()=>{\n" + 
                "                        node.setAttribute('class','nav_item_activated');\n" + 
                "                    });\n" + 
                "            \n" + 
                "                    node.addEventListener('mouseout',()=>{\n" + 
                "                        node.setAttribute('class','nav_item_desactivated');\n" + 
                "                    });\n" + 
                "                }\n" + 
                "                \n" + 
                "                for(let screen of screens){\n" + 
                "                    let node_view = document.getElementById(screen.view_id);\n" + 
                "                    \n" + 
                "                    for(let tab of screen.tabs){\n" + 
                "                        let node = document.getElementById(tab.id);\n" + 
                "                        node.addEventListener('click',()=>{\n" + 
                "                            node_view.innerHTML = tab.content;\n" + 
                "            \n" + 
                "                            for(let tab2 of screen.tabs){\n" + 
                "                                let node = document.getElementById(tab2.id);\n" + 
                "            \n" + 
                "                                if(tab.id == tab2.id)\n" + 
                "                                    node.setAttribute('class','tab_activated');\n" + 
                "                                else\n" + 
                "                                    node.setAttribute('class','tab_desactivated');\n" + 
                "                            }\n" + 
                "                        });\n" + 
                "                    }\n" + 
                "                }\n" + 
                "            }\n" + 
                "            \n" + 
                "            \n" + 
                "            window.addEventListener('load', ()=>setup())\n" + 
                "        </script>\n" + 
                "    </head>\n" + 
                "    <body>  \n" + 
                "\n" + 
                "        <div class=\"margin5\">\n" + 
                "            <div class=\"column_center\">\n" + 
                "                <p class=\"title\">Erdline</p>\n" + 
                "                <p class=\"text1\"><i>version: 1.1</i></p>\n" + 
                "            </div>\n" + 
                "        </div>\n" + 
                "\n" + 
                "                \n" + 
                "        <div class=\"nav\">\n" + 
                "            <a id =\"ref1\" class=\"nav_item_desactivated\" + href=\"#ancre1\"> Configuration </a>\n" + 
                "        </div>\n" + 
                "\n" + 
                "        \n" + 
                "        <div class=\"margin1\">\n" + 
                "            <div class=\"margin2\">\n" + 
                "                <p class=\"text2\"><b>Serveurs</b></p>\n" + 
                "                <p class=\"text1 indented\"> https:127.0.0.1:8080 </p>\n" + 
                "            </div>\n" + 
                "                        \n" + 
                "            <div>\n" + 
                "                <div class=\"margin2\">\n" + 
                "                    <a name =\"ancre1\" class=\"text4\">Configuration</a>\n" + 
                "                    <div>\n" + 
                "                        <div class=\"margin3\">\n" + 
                "                            <div>\n" + 
                "                                <p class=\"borange method_badge\"> PUT </p>\n" + 
                "                                <p class=\"text2 demi_indented inline\"> /config/gatling/{project} </p>\n" + 
                "                                <div class=\"line\">\n" + 
                "                                    <div class=\"column_left max_width_1\">\n" + 
                "                                        <div class=\"indented\">\n" + 
                "                                            <p class=\"text2\"> <b>Paramètres</b> </p>\n" + 
                "                                            <div class=\"indented\">\n" + 
                "                                                <p class=\"text1\"> project (Path) </p>\n" + 
                "                                                <p class=\"text3 indented\"> <i>Exemple: CustomerWS </i> </p>\n" + 
                "                                            </div>\n" + 
                "                                        </div>\n" + 
                "                                        <div class=\"indented\">\n" + 
                "                                            <p class=\"text2\"> <b>Codes de retour</b> </p>\n" + 
                "                                            <div class=\"indented\">\n" + 
                "                                                <p class=\"status_badge bgreen\"> 204 </p>\n" + 
                "                                                <p class=\"status_badge bred\"> 404 </p>\n" + 
                "                                            </div>\n" + 
                "                                        </div>\n" + 
                "                                    </div>\n" + 
                "                                    <div class=\"column_left\">\n" + 
                "                                        <div class=\"indented\">\n" + 
                "                                            <p class=\"text2\"> <b>Exemples</b> </p>\n" + 
                "                                            <div class=\"margin4 indented\">\n" + 
                "                                                <p id=\"endpoint_2_1\" class=\"tab_activated\"> body </p>\n" + 
                "                                                <div class=\"tab_div\">\n" + 
                "                                                    <p id=\"endpoint_2_2\" class=\"tab_text\"> {<br/>&quot;injectionRandomized&quot;:&nbsp;false,<br/>&quot;periods&quot;:&nbsp;[&quot;duration&quot;:&nbsp;60,&quot;requestByMn&quot;:&nbsp;1500}]<br/>}; </p>\n" + 
                "                                                </div>\n" + 
                "                                            </div>\n" + 
                "                                        </div>\n" + 
                "                                    </div>\n" + 
                "                                </div>\n" + 
                "                            </div>\n" + 
                "                        </div>\n" + 
                "                    </div>\n" + 
                "                </div>\n" + 
                "            </div>\n" + 
                "\n" + 
                "        </div>\n" + 
                "    </body>\n" + 
                "</html>";
}
