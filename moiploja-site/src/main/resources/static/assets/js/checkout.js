function prepareCCHash(){

    var cc = new Moip.CreditCard({
          number  : $("#ccNumber").val(),
          cvc     : $("#cvv").val(),
          expMonth: $("#expire_month").val(),
          expYear : $("#expire_year").val(),
          pubKey  : $("#public_key").val()
        });

    if(cc.isValid()){
        $("#ccNumber").val("123");
        $("#cvv").val("123");
        $("#ccHash").val(cc.hash());
        //alert("O Hash: " + $("#ccHash").val());
        return true;
    }else{
        $("#ccHash").val('INVALID');
        alert("Hash invalido");
        return false; //dont submit the form;
    }

}

$(document).ready(function() {
  $("#place_order").click(function(event) {
    return prepareCCHash();
  });
});
