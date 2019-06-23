package com.example.petclinic;

import com.example.petclinic.controller.OwnerController;
import com.example.petclinic.controller.PetController;
import com.example.petclinic.controller.VetController;
import com.example.petclinic.controller.VisitController;
import com.example.petclinic.model.*;
import com.example.petclinic.repository.OwnerRepository;
import com.example.petclinic.repository.PetRepository;
import com.example.petclinic.repository.VetRepository;
import com.example.petclinic.repository.VisitRepository;
import com.example.petclinic.service.OwnerService;
import com.example.petclinic.service.PetService;
import com.example.petclinic.service.VetService;
import com.example.petclinic.service.VisitService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PetClinicDriver {

    public static void main(String[] args) {

        testApp();

    }

    private static void testApp() {

        // Owner dependency injection (DI) setup
        OwnerRepository ownerRepository = new OwnerRepository();
        OwnerService ownerService = new OwnerService(ownerRepository);
        OwnerController ownerController = new OwnerController(ownerService);

        PetRepository petRepository = new PetRepository();
        PetService petService = new PetService(petRepository);
        PetController petController = new PetController(petService);

        VisitRepository visitRepository = new VisitRepository();
        VisitService visitService = new VisitService(visitRepository);
        VisitController visitController = new VisitController(visitService);

        VetRepository vetRepository = new VetRepository();
        VetService vetService = new VetService(vetRepository);
        VetController vetController = new VetController(vetService);


        // ***** Owner testing *****

        // create our owners
        Owner owner1 = new Owner(1, "Homer Simpson", "742 Evergreen Terrace", "Springfield", "9395550113");
        Owner owner2 = new Owner(2, "Marge Simpson", "742 Evergreen Terrace", "Springfield", "9395550113");
        Owner owner3 = new Owner(3, "Lisa Simpson", "742 Evergreen Terrace", "Springfield", "9395550113");
        Owner owner4 = new Owner(4, "Bart Simpson", "742 Evergreen Terrace", "Springfield", "9395550113");

        // save owners to database
        ownerController.add(owner1);
        ownerController.add(owner2);
        ownerController.add(owner3);
        ownerController.add(owner4);

        // get all owners from database and display them
        List<Owner> owners = ownerController.getAll();
        display(owners);

        // create some pets and add them to an existing owner
        Pet pet1 = new Pet(1, "Godzilla", new Date(), PetType.LIZARD);
        Pet pet2 = new Pet(2, "Santa's Little Helper", new Date(), PetType.DOG);
        owner4.addPet(pet1);
        owner4.addPet(pet2);

        // display the owner info again
        display(owners);

        Pet pet3 = new Pet(1, "Strangles", new Date(), PetType.SNAKE);
        Pet pet4 = new Pet(1, "Stompy", new Date(), PetType.ELEPHANT);

        petController.add(pet1);
        petController.add(pet2);
        petController.add(pet3);
        petController.add(pet4);

        display(petController.getAll());

        // ***** Visit *****

        Visit visit1 = new Visit(1, new Date(), "description", pet1);
        Visit visit2 = new Visit(2, new Date(), "description", pet2);

        visitController.add(visit1);
        visitController.add(visit2);

        display(visitController.getAll());

        // ***** Vet *****
        List<Speciality> specialities = new ArrayList<Speciality>() {{
            add(Speciality.DENTISTRY);
            add(Speciality.RADIOLOGY);
        }};

        List<Visit> visits = new ArrayList<Visit>() {{
            add(visit1);
            add(visit2);
        }};

        Vet vet1 = new Vet(1, "Veterinarian", specialities , visits);

        vetController.add(vet1);

        display(vetController.getAll());




    }

    private static void display(Object obj) {

        if (obj instanceof List) {

            List<?> list = (List) obj;
            for (Object o : list) {
                System.out.println("\t" + o);
            }

        } else {

            System.out.println(obj);

        }

        System.out.println();
    }
}
