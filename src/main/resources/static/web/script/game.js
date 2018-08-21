

function getQueryVariable(gp) {
    let sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;
    for (let i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split("=");
        if (sParameterName[0] === gp) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }

}

var gp = getQueryVariable("gp");
console.log(gp)

function createUrl() {
    var goodURL = "http://localhost:8080/api/game_view/" + gp;
    console.log(goodURL);
    return goodURL;
}

//fetching data
var myGameData = $.ajax({
    dataType: "json",
    url: createUrl(),
    })
    .done(function (data) {
        console.log(data);
        
        //getting ships objects
        let ships = data.ships;
        console.log("ships", ships);
        
        let shipLocations;
        
        function getShipsLocation(ships,shipLocations) {
            for (let i = 0; i < ships.length; i++) {
                shipLocations = ships[i].locations;
                console.log("location",shipLocations)
                shipArrs.push(shipLocations)
            }
                   
        }
        getShipsLocation(ships,shipLocations);

        getLocationsArr(shipArrs);

        gridColors("my-cell",shiplocationsArr,"background-color","rgba(97, 51, 124, 0.8)");
        
        //Createing infos for each player,including: id, userName and all salvo locations.
        let me = {};
        let opponent = {};
        
        data.gamePlayers.forEach(p => {
            let player = p.player
            player.locations = {}
            if (player.id === data.id) {
                me = player;
            } else {
                opponent = player;
            }
        })

        function playersHTML() {
            let myNameHtml = document.getElementById("my-name");
            let otherNameHtml = document.getElementById("opponent-name")
          return    myNameHtml.innerHTML = me.userName,
                    otherNameHtml.innerHTML = opponent.userName;
        }
        playersHTML();

        /*
        
            {
                "userName":"",
                "locations":{
                    "F1":{"ship":true},
                    "G1":{"ship":true, "salvo":1},
                    "F5":{"salvo":2},
                    "J3":{"salvo":1},
                }
            }
        }
        */

        function getSalvos(data) { 
            console.log("salvos",data.salvos);
            data.salvos.forEach(salvos => {
                salvos.forEach(salvo => {
                    let player = opponent
                    if(salvo.player === me.userName) {
                        player = me
                    }

                    salvo.locations.forEach(loc => {
                        let info = player.locations[loc]
                        if (info == undefined) {
                            info = {}
                        }
                        info["salvo"] = salvo.turn
                        player.locations[loc] = info
                        
                    })
                })
            });
        }
        getSalvos(data);


        
        function demoSalvos(player,opClassName){
            let locs = player.locations;
            // let opSalvos = oppoPlayer.locations;
            let opCells = document.getElementsByClassName(opClassName);
            // let selfCells = document.getElementsByClassName(selfClassName);
            let ships = shiplocationsArr
            //console.log("cells",cells)
            for(key in locs){
                if(locs.hasOwnProperty(key)){
                    //salvosArr.push(key)
                    for(let i=0;i< opCells.length; i++){
                        let cell = opCells[i]
                        if(cell.id == key){
                          $(cell).css("background-image", "url(style/material/angrybird-"+ locs[key].salvo +".png)")
                          cell.innerHTML = locs[key].salvo
                        
                        }
                       
                    }
                    console.log(locs[key].salvo)
                }
           }

        //    for(key in opSalvos) {
        //        if(opSalvos.hasOwnProperty(key)){
        //            for (let j = 0; j < selfCells.length; j++) {
        //                let selfCell = selfCells[j];
        //                ships.forEach(el=>{
        //                 if(el == key&& selfCell.id == key) {
        //                     $(selfCell).css("background-color", "rgba(255, 105, 29,0.8)");
        //                 }
        //             })
                       
        //            }
        //        }
        //    }
        }

        //console.log("oppoSalvosLoc",oppoSalvosLoc)

        demoSalvos(me, "oppo-cell");
        demoSalvos(opponent, "my-cell"); 
        console.log(me)
        console.log(opponent)
        
        function getHits(opponent,cellClassName,shiplocationsArr){
            let shots = opponent.locations;
            let selfCells = document.getElementsByClassName(cellClassName);
            let ships = shiplocationsArr;
            for(key in shots) {
                if(shots.hasOwnProperty(key)){
                    for (let i = 0; i < selfCells.length; i++) {
                        let selfCell = selfCells[i];
                        ships.forEach(el=>{
                            if(el == key&& selfCell.id == key){
                                $(selfCell).css("background-color","rgba(255, 105, 29,0.8)")
                            }
                        })
                        
                    }
                }
            }
             
        }

        getHits(opponent,"my-cell",shiplocationsArr)
    

    })
    .fail(function (jqXHR, textStatus) {
        console.log("Failed: " + textStatus);
    });




// creating grid/table
let theaders = new Array();

function getTable() {

    for (let i = 0; i < 10; i++) {
        theaders.push(i + 1);
    }
    return theaders;
}

getTable();

function getHeadersHtml(theaders) {
    return "<tr><th></th>" + theaders.map(function (theader) {
        return "<th>" + theader + "</th>";
    }).join("") + "</tr>";
}

function renderHeaders() {
    let headerHtml = getHeadersHtml(theaders);
    document.getElementById("ship-table-headers").innerHTML = headerHtml;
    document.getElementById("salvo-table-headers").innerHTML = headerHtml;

}
renderHeaders();

let rowHeaders = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J"]


function getColumnsHtml(theader) {
    return theader.map(function () {

        return "<td>" + ""
            + "</td>";

    }).join("")
}



function getRowsHtml(rowHeaders) {
    return rowHeaders.map(function (rowHeader) {
        return "<tr><th>" + rowHeader + "</th>" +
            getColumnsHtml(theaders) + "</tr>";
    }).join("");
}

function renderRows(rowHeaders) {
    let html = getRowsHtml(rowHeaders);
    document.getElementById("ship-table-rows").innerHTML = html;
    document.getElementById("salvo-table-rows").innerHTML = html;
}

renderRows(rowHeaders);

//creating td innerTHML values 
function tdVals(id,cellName) {
    let x = document.getElementById(id).rows
    //console.log(x)
    for (let i = 1; i < x.length; i++) {
        let rows = x[i].cells;
        for (let j = 1; j < rows.length; j++) {
            rows[j].setAttribute("id",rowHeaders[i - 1]+ theaders[j - 1]);
            rows[j].setAttribute("class",cellName);
    
        }
       
    }

}

tdVals("my-table", "my-cell");
tdVals("oppo-table", "oppo-cell");

//Create Conditional Grid Colors 

let shipArrs = [];
let shiplocationsArr = [];


function getLocationsArr(shipArrs) {
    let eachLo;
    for (let i = 0; i < shipArrs.length; i++) {
       let newArr = shipArrs[i];
       //console.log("newArr", newArr)
       for (let j = 0; j < newArr.length; j++) {
        //    console.log("each",newArr[j] )
        eachLo = newArr[j];
        shiplocationsArr.push(eachLo)
       }
    }
   
}


function gridColors(className,loArr,styleProp, styleVal){
    let cells = document.getElementsByClassName(className);
  
    for (i = 0; i < cells.length; i++) {
        var tds = cells[i];
        for (j = 0; j < loArr.length; j++) {
            
            if (tds.id == loArr[j]) {
                $(tds).css(styleProp, styleVal)
            }
        }
    }
}

//HTML background-------------

particlesJS("particles-js", {
    "particles": {
      "number": {
        "value": 100,
        "density": {
          "enable": true,
          "value_area": 800
        }
      },
      "color": {
        "value": "#ffffff"
      },
      "shape": {
        "type": "circle",
        "stroke": {
          "width": 0,
          "color": "#000000"
        },
        "polygon": {
          "nb_sides": 5
        },
        "image": {
          "src": "img/github.svg",
          "width": 100,
          "height": 80
        }
      },
      "opacity": {
        "value": 0.5,
        "random": false,
        "anim": {
          "enable": false,
          "speed": 1,
          "opacity_min": 0.1,
          "sync": false
        }
      },
      "size": {
        "value": 3,
        "random": true,
        "anim": {
          "enable": false,
          "speed": 40,
          "size_min": 0.1,
          "sync": false
        }
      },
      "line_linked": {
        "enable": true,
        "distance": 150,
        "color": "#5A03AF",
        "opacity": 0.4,
        "width": 1
      },
      "move": {
        "enable": true,
        "speed": 6,
        "direction": "none",
        "random": false,
        "straight": false,
        "out_mode": "out",
        "bounce": false,
        "attract": {
          "enable": false,
          "rotateX": 600,
          "rotateY": 1200
        }
      }
    },
    "interactivity": {
      "detect_on": "canvas",
      "events": {
        "onhover": {
          "enable": true,
          "mode": "grab"
        },
        "onclick": {
          "enable": true,
          "mode": "push"
        },
        "resize": true
      },
      "modes": {
        "grab": {
          "distance": 140,
          "line_linked": {
            "opacity": 1
          }
        },
        "bubble": {
          "distance": 400,
          "size": 40,
          "duration": 2,
          "opacity": 8,
          "speed": 3
        },
        "repulse": {
          "distance": 200,
          "duration": 0.4
        },
        "push": {
          "particles_nb": 4
        },
        "remove": {
          "particles_nb": 2
        }
      }
    },
    "retina_detect": true
  });
  
