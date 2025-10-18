import { Component } from '@angular/core';
import { BookStats } from '../../../core/models/book.interface';
import { StatisticsService } from '../../../core/services/statistics.service';
import { CommonModule } from '@angular/common';


//PrimeNg
import { CardModule } from 'primeng/card';

@Component({
  selector: 'app-statistics',
  imports: [CardModule, CommonModule],
  templateUrl: './statistics.html',
  styleUrl: './statistics.scss'
})
export class Statistics {
  stats: BookStats[] = [];

  constructor(private statsService: StatisticsService) {}

  ngOnInit() {
    this.statsService.getBookLoanStats().subscribe(data => this.stats = data);
  }
}
