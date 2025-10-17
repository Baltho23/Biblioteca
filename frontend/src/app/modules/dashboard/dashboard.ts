import { Component } from '@angular/core';
import { NavigationEnd, Router, RouterLink, RouterOutlet } from '@angular/router';
import { DrawerModule } from 'primeng/drawer';

//PrimeNg
import { ButtonModule } from 'primeng/button';
import { filter } from 'rxjs';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [ButtonModule, DrawerModule, RouterOutlet, RouterLink],
  templateUrl: './dashboard.html',
  styleUrls: ['./dashboard.scss']
})
export class Dashboard {
  visible: boolean = false;
  title: string = 'Gestión de libros';

  constructor(private router: Router) {
    this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe((event: any) => {
        const url = event.urlAfterRedirects;
        if (url.includes('books')) this.title = 'Libros';
        else if (url.includes('members')) this.title = 'Miembros';
        else if (url.includes('loans')) this.title = 'Préstamos';
        else if (url.includes('statistics')) this.title = 'Estadísticas';
        else this.title = 'Gestión de libros';
      });
  }
}
