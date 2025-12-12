export interface ProductSummary {
  productId: number;
  productName: string;
  quantity?: number;  // for frequent
  datePurchased?: string;  // for recent
}