//sidebar toggle
$(document).ready(function () {
    $("#sidebar-toggle").click(function () {
        $(".sidebar").toggleClass("active");
    });
    $(".menu-toggle").click(function () {
        $("body").toggleClass("widescreen");
    });
    //slim
    $('.nicescroll').slimScroll({
        height: '100%',
        railOpacity: 0.9
    });
//metis menu
    $("#menu").metisMenu();
    
    $(".content-page,.side-menu").equalize({
                });
//tooltips
$(function () {
    $('[data-toggle="tooltip"]').tooltip();
    $('[data-toggle="popover"]').popover();
});

var date = new Date();
var hours = date.getHours();
var minutes = date.getMinutes();
var ampm = hours >= 12 ? 'pm' : 'am';

var month = date.getMonth();
var day = date.getDate();
var year = date.getFullYear();
var dayname = date.getDay();

var monthNames = [ "January", "February", "March", "April", "May", "June",
    "July", "August", "September", "October", "November", "December" ];
var week=["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"]; 

document.getElementById("para1").innerHTML = hours+"."+minutes+ampm;
document.getElementById("para2").innerHTML = week[dayname];
document.getElementById("para3").innerHTML = day+" "+monthNames[month]+" "+year;
});   