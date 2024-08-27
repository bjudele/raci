// next prev
var divs = $('.show-section section');
var now = 0; // currently shown div
divs.hide().first().show(); // hide all divs except first

//show active step
function showActiveStep()
{
    if ($('#step1').is(':visible'))
    {
        $(".step-bar .fill").css('width', '20%');
    }
    else if ($('#step2').is(':visible'))
    {
        $(".step-bar .fill").css('width', '40%');
    }
    else if ($('#step3').is(':visible'))
    {
        $(".step-bar .fill").css('width', '60%');
    }
    else if ($('#step4').is(':visible'))
    {
        $(".step-bar .fill").css('width', '80%');
    }
    else if ($('#step5').is(':visible'))
    {
        $(".step-bar .fill").css('width', '100%');
    }
    else {
        console.log("error");
    }
}


function next()
{
    divs.eq(now).hide();
    now = (now + 1 < divs.length) ? now + 1 : 0;
    divs.eq(now).show(); // show next
    console.log(now);

    showActiveStep();
}
$(".prev").on('click', function()
{

    $('.radio-field').addClass('bounce-left');
    $('.radio-field').removeClass('bounce-right');
    $(".step-bar .bar .fill").eq(now).removeClass('w-100');
    divs.eq(now).hide();
    now = (now > 0) ? now - 1 : divs.length - 1;
    divs.eq(now).show(); // show previous
    console.log(now);

    showActiveStep();

})
$("#step1 .radio-field").on('click', function()
{
    $("#step1 .radio-field").parent().removeClass('active');
    $(this).parent().addClass("active")
})
$("#step2 .radio-field").on('click', function()
{
    $("#step2 .radio-field").parent().removeClass('active');
    $(this).parent().addClass("active")
})
$("#step3 .radio-field").on('click', function()
{
    $("#step3 .radio-field").parent().removeClass('active');
    $(this).parent().addClass("active")
})
$("#step4 .radio-field").on('click', function()
{
    $("#step4 .radio-field").parent().removeClass('active');
    $(this).parent().addClass("active")
})
$("#step5 .radio-field").on('click', function()
{
    $("#step5 .radio-field").parent().removeClass('active');
    $(this).parent().addClass("active")
})





// timer
var count = 60;

var interval = setInterval(function() 
{
  
  if(count == 0)
  {
    clearInterval(interval);
  }
  else 
  {
    count = count -1;
  }
  document.getElementById("countdown-timer").innerHTML = count;
},1000);


// quiz validation
var allDropdownsSelected = false;

function validateDropdowns(stepnumber){

    allDropdownsSelected = true;
    $("#step"+stepnumber+" select").each(function() {
        if ($(this).val() === "") {
            allDropdownsSelected = false;
            return false;
        }
    });

}




// form validation
$(document).ready(function()
{

    let currentStep = $(".step-count").attr("id");

    // check step1
    $("#step"+currentStep+"btn").on('click', function(e)
    {
        if($(this).parent().hasClass("locked")){
            return false;
        }
        validateDropdowns(currentStep);

        if(allDropdownsSelected === false) {
        e.preventDefault();
        console.log("nu e valid ");
        (function (el) {

            setTimeout(function () {
                el.children().remove('.reveal');
            }, 3000);
            }($('#error').append('<div class="reveal alert alert-danger">Please select an option for each role!</div>')));
            
            // validateDropdowns(currentStep);

        } else {
            console.log("e valid ");
            // $('#step'+currentStep+' .radio-field').removeClass('bounce-left');
            // $('#step'+currentStep+' .radio-field').addClass('bounce-right');
            // setTimeout(function()
            // {
            //     next();
            // }, 900)
            // countresult(currentStep);

        }


    })




    // check last step
    $("#sub").on('click', function()
    {
        validateDropdowns(5);

        if(allDropdownsSelected == false)
        {
            
        (function (el) {
            setTimeout(function () {
                el.children().remove('.reveal');
            }, 3000);
            }($('#error').append('<div class="reveal alert alert-danger">Choose an option!</div>')));
            
            validateDropdowns(5);

        }

        else
        {
            showresult();
            countresult(5);
            $("#sub").html('done');

        }
    })
})