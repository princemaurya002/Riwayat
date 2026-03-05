# Riwayat Unified Database Schema (V2.1)

This document defines the standardized Firestore structure for the Riwayat ecosystem (Mobile & Web).

## 1. Users Collection
- **Path**: `users/{userId}`
- **Fields**:
    - `name`: string
    - `email`: string
    - `profileUrl`: string
    - `businessName`: string
    - `brandTone`: string (e.g., 'artistic', 'professional')
    - `artisanId`: string (unique identifier for inventory linking)

## 2. Inventory Collection
- **Path**: `inventory/{artisanId}/products/{productId}`
- **Fields**:
    - `name`: string
    - `description`: string
    - `price`: number
    - `stock`: number
    - `imageUrl`: string
    - `metadata`: map (AI-generated tags, material types)

## 3. Marketing (Ads) Collection
- **Path**: `marketing/{artisanId}/ads/{adId}`
- **Fields**:
    - `productId`: string (reference to inventory)
    - `adSize`: string
    - `visualUrl`: string (Firebase Storage public URL)
    - `caption`: string
    - `hashtags`: array<string>
    - `createdAt`: timestamp

## 4. Ledger (Transactions) Collection
- **Path**: `ledger/{artisanId}/transactions/{txId}`
- **Fields**:
    - `type`: string ('INCOME' | 'EXPENSE')
    - `amount`: number
    - `productRef`: string (optional)
    - `note`: string
    - `timestamp`: timestamp

## 5. Community Collection
- **Path**: `community/marketplace/items/{itemId}`
- **Fields**:
    - `name`: string
    - `price`: string
    - `source`: string
    - `supplierContact`: string
