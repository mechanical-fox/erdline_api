
let nav_items;
let screens;

/* :nav-items-js */

/* :screens */


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