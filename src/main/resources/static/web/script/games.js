var gameData = $.ajax({
  dataType: "json",
  url:"http://localhost:8080/api/games",
}) 
.done(function(data) {
  gameData = data;
  console.log(gameData);
  showGames(gameData);

})
.fail(function( jqXHR, textStatus ) {
 console.log( "Failed: " + textStatus );
});



function showGames (gd) {
    let oList = document.getElementById("games");

    for(let i=0; i< gd.length; i++) {
    
      let gTime = gd[i].created;
      let createdTime = new Date(gTime).toLocaleString();
     
      let player1
      let player2 
       switch (gd[i].gamePlayers.length) {
        case 2:
          player1 = gd[i].gamePlayers[0].player.userName;
          player2 = gd[i].gamePlayers[1].player.userName;
          break;
        case 1:
          player1 = gd[i].gamePlayers[0].player.userName;
          player2 = "no participant";
          break;
        case 0:
          player1 = "no participant";
          player2 = "no participant";
         default:
           break;
       }
    
      let gameList = document.createElement("li");
      gameList.setAttribute("class","list-group-item")
      gameList.innerHTML = createdTime + ": " + " " +"Player1: "+ player1+ ", " +"Player2: " +player2;
        
        oList.append(gameList);
    }
    
}


// var leaderBoardData = $.ajax({
//   dataType:"json",
//   url:"http://localhost:8080/api/leader_board"
// })
// .done((data)=>{
//   console.log("Board: ", data)
//   let scoreObj = data;
//   for(key in scoreObj){
//     console.log(key)
//   }

  
// })
// .fail((textStatus)=>{
//   console.log("LeaderBoard Failed: "+ textStatus)
// });


// let userNames = new Array()



// function getColumnsHtml(theader) {
//   return theader.map(function () {

//       return "<td>" + ""
//           + "</td>";

//   }).join("")
// }


var leaderBoardData = new Vue({
  el:"#app",
  data:{
    url:"http://localhost:8080/api/leader_board",
    scores:[]

  },

  created: function(){
    this.getScores();
  },

  methods:{
    getScores: function () {
        fetch(this.url)
          .then(function(response){
            return response.json();
          })
          .then(function(data){
            let scoresData = data;
            console.log(scoresData)
            
            this.transformKeys(scoresData)
          }.bind(this))
    },

    transformKeys: function(scoresData){
      
      for (let i = 0; i< scoresData.length; i++) {
        let element = scoresData[i];
            console.log(element)
        let  scores = {
          userName: element.userName,
          total: element.total,
          wins:element.wins,
          losses: element.looses,
          ties:element.ties
        }
        this.scores.push(scores)
       
      }
        
  
    
    }

  }
})
