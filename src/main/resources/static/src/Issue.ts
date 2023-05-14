export class Issue {
    private elCreateIssue: Element;
    private elContents: Element;
    private elGnb: Element;

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

    public create(): void {
        console.log('create');
    }
}