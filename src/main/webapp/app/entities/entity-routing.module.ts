import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { TextMaskModule } from 'angular2-text-mask';

@NgModule({
  imports: [RouterModule.forChild([]), TextMaskModule],
})
export class EntityRoutingModule {}
