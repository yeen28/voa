import { RequestHelper } from './request/RequestHelper';
import { NewIssue } from './model/NewIssue';

export class IssueManager {
    private elCreateIssue: Element;
    private elContents: Element;
    private elGnb: Element;

    private request: RequestHelper = new RequestHelper();

    constructor() {
        this.elCreateIssue = document.getElementById('create-issue');
        this.elContents = document.getElementsByClassName('contents')[0];
        this.elGnb = document.getElementById('gnb');
    }

    public open(): void {
       this.elCreateIssue.classList.remove('hide');
       this.elGnb.classList.add('hidden');
       this.elContents.classList.add('hidden');
    }

    public cancel(): void {
        this.elCreateIssue.classList.add('hide');
        this.elGnb.classList.remove('hidden');
        this.elContents.classList.remove('hidden');
    }

    private getIssueTypeId(): string {
        const elIssueTypeSelect : HTMLSelectElement = document.getElementById('issue-type-select') as HTMLSelectElement;
        return elIssueTypeSelect.options[elIssueTypeSelect.selectedIndex].value
    }

    private getIssueRank(): string {
        const elIssueRankSelect : HTMLSelectElement = document.getElementById('issue-rank-select') as HTMLSelectElement;
        return elIssueRankSelect.options[elIssueRankSelect.selectedIndex].value
    }

    private getIssueOwner(): string {
        const elIssueOwnerSelect : HTMLSelectElement = document.getElementById('issue-owner-select') as HTMLSelectElement;
        return elIssueOwnerSelect.options[elIssueOwnerSelect.selectedIndex].value
    }

    private getIssueTitle(): string {
        const elTitleInput: HTMLInputElement = document.getElementById('issue-title-input') as HTMLInputElement;
        return elTitleInput.value;
    }

    private getIssueVersion(): string {
        const elVersionInput: HTMLInputElement = document.getElementById('issue-version-input') as HTMLInputElement;
        return elVersionInput.value;
    }

    private getIssueLabel(): string {
        const elLabelInput: HTMLInputElement = document.getElementById('issue-label-input') as HTMLInputElement;
        return elLabelInput.value;
    }

    private getIssueRelation(): string {
        const elRelationInput: HTMLInputElement = document.getElementById('issue-relation-input') as HTMLInputElement;
        return elRelationInput.value;
    }

    private getIssueEnv(): string {
        const elEnvContent: HTMLInputElement = document.getElementById('issue-env-content') as HTMLInputElement;
        return elEnvContent.value;
    }

    private getIssueDesc(): string {
        const elDescContent: HTMLInputElement = document.getElementById('issue-desc-content') as HTMLInputElement;
        return elDescContent.value;
    }

    public create(): void {
        const newIssueModel: NewIssue = new NewIssue(
            '1', //TODO projectId
            this.getIssueTypeId(),
            this.getIssueTitle(),
            this.getIssueLabel().split(','),
            this.getIssueVersion().split(','),
            this.getIssueEnv(),
            this.getIssueDesc(),
            '1', //TODO rank
            '1', //TODO ownerId
            '1', //TODO reporterId
            this.getIssueRelation()
        );

        const successFunc: Function = (res: Response) => {
            console.log(res);
            this.cancel();
        }

        const errorFunc: Function = (res: Response) => {
            console.log(res);
            this.cancel();
        }

        const issue = this.request.post('/issue', newIssueModel, successFunc, errorFunc);
    }
}