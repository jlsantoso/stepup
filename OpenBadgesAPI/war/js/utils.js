


function ycoordinates(imagename){
	if (imagename.indexOf("0phase") > -1) return padding*(0+manualrows)+shift_automatic;
	else if (imagename.indexOf("1phase") > -1) return padding*(1+manualrows)+shift_automatic;
	else if (imagename.indexOf("2phase") > -1) return padding*(2+manualrows)+shift_automatic;
	else if (imagename.indexOf("3phase") > -1) return padding*(3+manualrows)+shift_automatic;
	else if (imagename.indexOf("4phase") > -1) return padding*(4+manualrows)+shift_automatic;
	else if (imagename.indexOf("5phase") > -1) return padding*(5+manualrows)+shift_automatic;
	else if (imagename.indexOf("6phase") > -1) return padding*(6+manualrows)+shift_automatic;
	else{
		var row = Math.floor( manualbadges / 3);
		return (padding * row) + shift_manual ;
	}
}

function xcoordinates(imagename, type){
	if (imagename.indexOf("phase1") > -1) return padding*1;
	else if (imagename.indexOf("phase2") > -1) return padding*2;
	else if (imagename.indexOf("phase3") > -1) return padding*3;
	else {		
		var mod = manualbadges % 3;
		//type is 1 when we finish to draw the badge, the text and the person icon
		manualbadges=+type;
		return padding*(mod+1)
	}
}

function awardedBadges(badge){
	var badgesNumber = 0;
	badges_awarded_array.forEach(function(d) {
		if ((d.jsonBadge.badge.image == badge.jsonBadge.image)&&
				(d.jsonBadge.badge.name == badge.jsonBadge.name)&&
				(d.jsonBadge.badge.description == badge.jsonBadge.description)){
			badgesNumber++;
		}
	});
	return badgesNumber;
	
}

function awardedBadgesNames(badge){
	var usernames = "";
	badges_awarded_array.forEach(function(d) {
		if ((d.jsonBadge.badge.image == badge.jsonBadge.image)&&
				(d.jsonBadge.badge.name == badge.jsonBadge.name)&&
				(d.jsonBadge.badge.description == badge.jsonBadge.description)){
				users_array.forEach(function(data) {
					var user_id=data.oauthProvider.toLowerCase()+'_'+data.oauthId.toLowerCase();
					if ((d.username.toLowerCase() == user_id)){
						usernames += data.name+'<br/> ';
					}
				});
		}
	});
	return usernames;
	
}

function updateManualBadgesValues(){
	for (var i = 0; i < badges_array.length; i++) {
	    if ((badges_array[i].jsonBadge.image.indexOf("0phase") == -1)&&
	    		(badges_array[i].jsonBadge.image.indexOf("1phase") == -1)&&
	    		(badges_array[i].jsonBadge.image.indexOf("2phase") == -1)&&
	    		(badges_array[i].jsonBadge.image.indexOf("3phase") == -1)&&
	    		(badges_array[i].jsonBadge.image.indexOf("4phase") == -1)&&
	    		(badges_array[i].jsonBadge.image.indexOf("5phase") == -1)&&
	    		(badges_array[i].jsonBadge.image.indexOf("6phase") == -1)){
	    	manualbadges_total++;
	    }    
	}
	
	if (manualbadges_total>0) manualrows = Math.floor( manualbadges_total / 3) + 1;
}

function updateHeightValue(){
	if (badges_array.length<43){
		height = padding * (7+manualrows);
	}
}