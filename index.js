
  var firebaseConfig = {
    apiKey: "AIzaSyDZI34_ad7MKpf2HpvEKbYbKIctaQfyBu4",
    authDomain: "myfirebase-cdc27.firebaseapp.com",
    databaseURL: "https://myfirebase-cdc27.firebaseio.com",
    projectId: "myfirebase-cdc27",
    storageBucket: "myfirebase-cdc27.appspot.com",
    messagingSenderId: "92777432621",
    appId: "1:92777432621:web:4233543b3fff78c12b1aa4",
    measurementId: "G-RDQRC4PY3P"
  };

  // Initialize Firebase
  firebase.initializeApp(firebaseConfig);
  //firebase.analytics();

  const auth = firebase.auth();

  
  function signUp(){

    var email = document.getElementById("email");
    var password = document.getElementById("password");

    const promise = firebase.auth().createUserWithEmailAndPassword(email.value, password.value);
      promise.catch(e => alert(e.message));
      alert("Signed In");
  }

  function signIn(){
    var email = document.getElementById("email");
    var password = document.getElementById("password");
    const promise = firebase.auth().signInWithEmailAndPassword(email.value, password.value);
    promise.catch(e=> alert(e.message));
    alert("Signed In "+ email.value);
  }

  function signOut(){
    firebase.auth().signOut();
    document.getElementById("user_div").style.display = "none";
    alert("Logged Out")
  }
  

  firebase.auth().onAuthStateChanged(function(user) {
    if (user) {
      // User is signed in.
      document.getElementById("user_div").style.display = "block";
      document.getElementById("login_div").style.display = "none";
      document.getElementById("bodyStyle").style.background = "rgb(252, 226, 226)";
      document.getElementById("trafficImage").style.display = "none";

    } else {
      
      document.getElementById("user_div").style.display = "none";
      document.getElementById("login_div").style.display = "block";
      
    }
  });

  
    var rootRef = firebase.database().ref().child("DriversInfo");
    rootRef.on("child_added", snap => {
      var name = snap.child("name").val();
      var email = snap.child("email").val();
      var numberPlate = snap.child("numberPlate").val();
      var reportCount = snap.child("reportCount").val();
      if(reportCount>3)
      {
        $("#table_body").append("<tr><td>" + name + "</td><td>" + email + "</td><td>" + numberPlate + "</td><td>" + reportCount +
        "</td></tr>")
        "</td><td><button>Remove</button></td></tr>"
      }
     
      
    });
 


  



  function removeDriver(){
    
    const removeNumber = document.getElementById('removeDriver').value;
    var ref = firebase.database().ref('DriversInfo');
    ref.on('value', gotData, errData);
  
    function gotData(data){
      const driverObject = data.val();
      console.log(driverObject)
      const arr = Object.keys(driverObject)
      console.log(arr)

          var temp = 0;
          for ( var i = 0; i<arr.length; i++){
            if( removeNumber == arr[i]){
              temp = 1;
              
            }
          }
            if (temp == 0){
              alert(removeNumber + " not found")
            }
            else {
              var rootRef = firebase.database().ref().child("DriversInfo");
                const newData = {
                  reportCount: 0
                };
              rootRef.child(removeNumber).update(newData);
              window.location.reload()
              alert(removeNumber + " Removed")
            }
    

      }
      function errData(err){
        console.log('Error!')
        console.log(err)
      }
  }
  
    


  //   alert(removeNumber + "Removed")
  //   var rootRef = firebase.database().ref().child("DriversInfo");
  //   const newData = {
  //     reportCount: 0
  //   };
  //   rootRef.child(removeNumber).update(newData);
  //   window.location.reload()
  // }

  // document.getElementById('removeButton').addEventListener('click', (e) => {
  //     e.preventDefault();
  //     const newData = {
  //       reportCount: 0
  //     }
      
  //     rootRef.child(removeNumber).update(newData)
  // });

  

  



// DONOT DELETE

//   var ref = firebase.database().ref('DriversInfo');
//   ref.on('value', gotData, errData);

//   function gotData(data){
//     const driverObject = data.val();
//     console.log(driverObject)
//     const arr = Object.keys(driverObject)
//     console.log(arr)
//     for ( var i = 0; i<arr.length; i++){
//       var key = arr[i];
//       const name = driverObject[key].name;
//       const email = driverObject[key].email;
//       const numberPlate = driverObject[key].numberPlate;
//       const reportCount = driverObject[key].reportCount;

//       var table = document.createElement('table').appendChild
//       var tr = document.createElement('tr')
//       const td = document.createElement('td', name)
//       tr.appendChild(td)
//     }
// }

//   function errData(err){
//     console.log('Error!')
//     console.log(err)
//   }

 
  
