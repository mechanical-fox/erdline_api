
let nav_items = ["nav_item_1","nav_item_2"];

/** ATTENTION il va falloir penser à échapper le contenu en Html avant :p */
let screens = 
[
    {
        "view_id" : "tab_1_3",
        "tabs" : [
                    {
                        name : "body",
                        id : "tab_1_1",
                        content : "aa<br/>bb"
                    },
                    {
                        name : "reponse",
                        id : "tab_1_2",
                        content : "/public/report106/index.html"
                    }
                ]
    },
    {
        "view_id" : "tab_2_3",
        "tabs" : [
                    {
                        name : "body",
                        id : "tab_2_1",
                        content : "cc<br/>dd"
                    },
                    {
                        name : "reponse",
                        id : "tab_2_2",
                        content : "ee<br/>ff"
                    }
                ]
    }
];


function setup(){

    for(let nav_item of nav_items){
        let node = document.getElementById(nav_item);
        
        node.addEventListener('mouseover',()=>{
            node.setAttribute('class','nav_item_activated');
        });

        node.addEventListener('mouseout',()=>{
            node.setAttribute('class','nav_item_desactivated');
        });
    }
    
    for(let screen of screens){
        let node_view = document.getElementById(screen.view_id);
        
        for(let tab of screen.tabs){
            let node = document.getElementById(tab.id);
            node.addEventListener('click',()=>{
                node_view.innerHTML = tab.content;

                for(let tab2 of screen.tabs){
                    let node = document.getElementById(tab2.id);

                    if(tab.id == tab2.id)
                        node.setAttribute('class','tab_activated');
                    else
                        node.setAttribute('class','tab_desactivated');
                }
            });
        }
    }
}


window.addEventListener('load', ()=>setup())