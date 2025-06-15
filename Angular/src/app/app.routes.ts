import { Routes } from '@angular/router';
import { CnxComponent } from './cnx/cnx.component';
import { AdminCompComponent } from './admin-comp/admin-comp.component';
import { MedCompComponent } from './med-comp/med-comp.component';
import { SecCompComponent } from './sec-comp/sec-comp.component';
import { PwdComponent } from './cnx/pwd/pwd.component';
import { AdminAddComponent } from './admin-comp/admin-add/admin-add.component';
import { AdminMedComponent } from './admin-comp/admin-med/admin-med.component';
import { AdminSecComponent } from './admin-comp/admin-sec/admin-sec.component';
import { AdminRvComponent } from './admin-comp/admin-rv/admin-rv.component';
import { AdminHomeComponent } from './admin-comp/admin-home/admin-home.component';
import { AdminMenuComponent } from './admin-comp/admin-menu/admin-menu.component';
import { MedHomeComponent } from './med-comp/med-home/med-home.component';
import { MedAllRvComponent } from './med-comp/med-all-rv/med-all-rv.component';
import { MedRvComponent } from './med-comp/med-rv/med-rv.component';
import { SecHomeComponent } from './sec-comp/sec-home/sec-home.component';
import { AddedRvComponent } from './sec-comp/added-rv/added-rv.component';
import { SecRvComponent } from './sec-comp/sec-rv/sec-rv.component';
import { AddRvComponent } from './sec-comp/add-rv/add-rv.component';
import { SecMedComponent } from './sec-comp/sec-med/sec-med.component';
import { AllRvComponent } from './sec-comp/all-rv/all-rv.component';

export const routes: Routes = [
    {
        path : "cnx",
        component : CnxComponent
    },
    {
        path : "admin",
        component : AdminCompComponent,
        children : [
            {path : "add" , component : AdminAddComponent},
            {path : "med" , component : AdminMedComponent},
            {path : "sec" , component : AdminSecComponent},
            {path : "rv" , component : AdminRvComponent},
            {path : "listAdmin" , component : AdminMenuComponent},
            {path : "home" , component : AdminHomeComponent},
            {path : "",redirectTo : "home",pathMatch : "full"}
        ]
    },
    {
        path : "med",
        component : MedCompComponent,
        children : [
            {path : "home",component : MedHomeComponent},
            {path : "allRv",component : MedAllRvComponent},
            {path : "nowRv",component : MedRvComponent},
            {path : "",redirectTo : "home",pathMatch : "full"}
        ]
    },
    {
        path : "sec",
        component : SecCompComponent,
        children : [
            {path : "home" , component : SecHomeComponent},
            {path : "add" , component : AddRvComponent},
            {path : "rv" , component : AllRvComponent},
            {path : "med" , component : SecMedComponent},
            {path : "todayRv" , component : SecRvComponent},
            {path : "fixedRv" , component : AddedRvComponent},
            {path : "",redirectTo : "home",pathMatch : "full"}
        ]
    },
    {
        path : "cnx/pwd",
        component : PwdComponent
    },
    {
        path : "",
        redirectTo : "cnx",
        pathMatch : "full"
    }
];
