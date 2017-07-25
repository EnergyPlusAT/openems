import { Component, Input, OnChanges } from '@angular/core';
import { FormGroup } from '@angular/forms';

import { Controller } from '../controller';
import { TemplateHelper, Device, Meta } from '../../../../shared/shared';
import { Role, ROLES } from '../../../../shared/type/role';

interface ArrayPath {
    formArray: string,
    index: number
}

interface ControlNamePath {
    formControlName: string
}

@Component({
    selector: 'forminput',
    templateUrl: './forminput.component.html'
})
export class FormInputComponent {

    @Input()
    public form: FormGroup;

    @Input()
    public arrayName: string;

    @Input()
    public controlName: number | string;

    @Input()
    public meta: any

    @Input()
    public allMeta: Meta;

    public type: string;
    public specialType: string;

    constructor(public tmpl: TemplateHelper) { }

    ngOnChanges(changes: any) {
        switch (this.meta.type) {
            case 'Boolean':
                this.specialType = 'boolean';
                break;
            case 'Integer':
            case 'Long':
                this.type = 'number';
                break;
            case 'Ess':
            case 'Meter':
            case 'RealTimeClock':
            case 'Charger':
                this.specialType = 'selectNature';
                this.type = this.meta.type + 'Nature';
                break;
            case 'JsonArray':
                this.specialType = 'ignore';
                break;
            default:
                console.log("Unknown type: " + this.meta.type);
                this.type = 'string';
        }
    }
}