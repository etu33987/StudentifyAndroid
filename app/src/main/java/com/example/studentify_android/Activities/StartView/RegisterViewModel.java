package com.example.studentify_android.Activities.StartView;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

// CE VIEWMODEL EST INUTILE CAR ANDROID PREND EN CHARGE
// LA MISE A JOUR DES ELEMENTS QUI SE TROUVE DANS UN SCROLLVIEW
// (OU A LINEALAYOUT (l'un des deux))
// (JE LAISSE CETTE CLASSE POUR MONTRER QUE JE SAIS M'EN SERVIR (S'il faut le montrer))

public class RegisterViewModel extends ViewModel {

    private MutableLiveData<String> email;
    private MutableLiveData<String> password;
    private MutableLiveData<String> confirmPassword;
    private MutableLiveData<String> name;
    private MutableLiveData<String> firstname;
    private MutableLiveData<String> birthdate;
    private MutableLiveData<String> phone;
    private MutableLiveData<String> street;
    private MutableLiveData<String> streetNumber;
    private MutableLiveData<String> city;
    private MutableLiveData<String> postalCode;
    private MutableLiveData<String> box;
    private MutableLiveData<String> floor;

    public MutableLiveData<String> getEmail() {
        if(email == null) {
            email = new MutableLiveData<>();
        }
        return email;
    }

    public MutableLiveData<String> getPassword() {
        if(password == null) {
            password = new MutableLiveData<>();
        }
        return password;
    }

    public MutableLiveData<String> getConfirmPassword() {
        if(confirmPassword == null) {
            confirmPassword = new MutableLiveData<>();
        }
        return confirmPassword;
    }

    public MutableLiveData<String> getName() {
        if(name == null) {
            name = new MutableLiveData<>();
        }
        return name;
    }

    public MutableLiveData<String> getFirstname() {
        if(firstname == null) {
            firstname = new MutableLiveData<>();
        }
        return firstname;
    }

    public MutableLiveData<String> getBirthdate() {
        if(birthdate == null) {
            birthdate = new MutableLiveData<>();
        }
        return birthdate;
    }

    public MutableLiveData<String> getPhone() {
        if(phone == null) {
            phone = new MutableLiveData<>();
        }
        return phone;
    }

    public MutableLiveData<String> getStreet() {
        if(street == null) {
            street = new MutableLiveData<>();
        }
        return street;
    }

    public MutableLiveData<String> getStreetNumber() {
        if(streetNumber == null) {
            streetNumber = new MutableLiveData<>();
        }
        return streetNumber;
    }

    public MutableLiveData<String> getCity() {
        if(city == null) {
            city = new MutableLiveData<>();
        }
        return city;
    }

    public MutableLiveData<String> getPostalCode() {
        if(postalCode == null) {
            postalCode = new MutableLiveData<>();
        }
        return postalCode;
    }

    public MutableLiveData<String> getBox() {
        if(box == null) {
            box = new MutableLiveData<>();
        }
        return box;
    }

    public MutableLiveData<String> getFloor() {
        if(floor == null) {
            floor = new MutableLiveData<>();
        }
        return floor;
    }

    public void setEmail(String email) {
        this.email.setValue(email);
    }

    public void setPassword(String password) {
        this.password.setValue(password);
    }

    public void setConfirmPassword(String password) {
        this.confirmPassword.setValue(password);
    }

    public void setName(String name) {
        this.name.setValue(name);
    }

    public void setFirstname(String firstname) {
        this.firstname.setValue(firstname);
    }

    public void setBirthdate(String birthdate) {
        this.birthdate.setValue(birthdate);
    }

    public void setPhone(String phone) {
        this.phone.setValue(phone);
    }

    public void setStreet(String street) {
        this.street.setValue(street);
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber.setValue(streetNumber);
    }

    public void setCity(String city) {
        this.city.setValue(city);
    }

    public void setPostalCode(String postalCode) {
        this.postalCode.setValue(postalCode);
    }

    public void setBox(String box) {
        this.box.setValue(box);
    }

    public void setFloor(String floor) {
        this.floor.setValue(floor);
    }
}
