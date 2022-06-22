import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { DemoFlexyModule } from "../demo-flexy-module";
import { DashboardComponent } from "./dashboard.component";
import { ActivityComponent } from "./dashboard-components/activity/activity.component";
import { ProductComponent } from "./dashboard-components/product/product.component";
import { CardsComponent } from "./dashboard-components/cards/cards.component";
import { FormsModule } from "@angular/forms";

@NgModule({
  declarations: [
    DashboardComponent,
    ActivityComponent,
    ProductComponent,
    CardsComponent,
  ],
  imports: [CommonModule, DemoFlexyModule, FormsModule],
  exports: [DashboardComponent, ActivityComponent, ProductComponent],
})
export class DashboardModule {}
