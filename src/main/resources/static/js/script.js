console.log("Script loaded");


//change theme work
let currentTheme=getTheme();
//initial-->
document.addEventListener('DOMContentLoaded', () =>{
    changeTheme();


});


//TODO:
function changeTheme(){
    //console.log(currentTheme);
   //set to web page
   changePageTheme(currentTheme,currentTheme);
    
    //set the listener to change theme button
    const changeThemeButton=document.querySelector('#theme_change_button');
    changeThemeButton.querySelector('span').textContent=currentTheme=="light"? "Dark":"Light";
  

    changeThemeButton.addEventListener("click",(event)=> {
        let oldTheme=currentTheme;
       
        console.log("change theme button clicked");
        if(currentTheme=="dark"){
            currentTheme="light";
        }
        else{
            currentTheme="dark";
        }
        console.log(currentTheme);
        changePageTheme(currentTheme,oldTheme);
        

    });
    //end of listener

}

//set theme to local storage
function setTheme(theme){
    localStorage.setItem("theme",theme);
}

//get theme from local storage
function getTheme(){
    let theme=localStorage.getItem("theme");
    return theme ? theme : "light";
}
//change current page theme
function changePageTheme(theme,oldTheme){
    // do the updations on local storage 
    setTheme(currentTheme);
    //remove the old theme
    document.querySelector("html").classList.remove(oldTheme);
    //add the new theme
    document.querySelector("html").classList.add(theme);
    //change the text of button
    document.querySelector("#theme_change_button").querySelector("span").textContent=theme=="light"? "Dark":"Light";


}
//end of change theme work
